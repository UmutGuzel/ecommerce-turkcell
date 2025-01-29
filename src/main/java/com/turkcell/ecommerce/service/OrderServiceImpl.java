package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.order.OrderCreateRequest;
import com.turkcell.ecommerce.dto.order.OrderResponse;
import com.turkcell.ecommerce.dto.orderstatus.UpdateOrderStatusDto;
import com.turkcell.ecommerce.entity.*;
import com.turkcell.ecommerce.mapper.CartMapper;
import com.turkcell.ecommerce.mapper.OrderMapper;
import com.turkcell.ecommerce.repository.*;
import com.turkcell.ecommerce.rules.OrderBusinessRules;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private  CartService cartService;
    @Autowired
    private  ProductService productService;
    @Autowired
    private  OrderStatusService orderStatusService;
    @Autowired
    private  OrderMapper orderMapper;
    @Autowired
    private  OrderBusinessRules orderBusinessRules;

    private  final CartMapper cartMapper;


    public OrderServiceImpl(CartMapper cartMapper) {
        this.cartMapper = cartMapper;

    }


    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public OrderResponse createOrder(OrderCreateRequest request) {
        String email = getCurrentUserEmail();

        orderBusinessRules.validateEmailFormat(email);

        Cart cart = cartService.getCartByIdAndUserEmail(request.getCartId(), email);

        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new BusinessException("Cart is empty");
        }

        // Validate stock for all products in the cart
        Map<UUID, Integer> productQuantities = cartItems.stream()
                .collect(Collectors.toMap(
                        item -> item.getProduct().getId(),
                        CartItem::getQuantity
                ));

        List<String> stockErrors = productService.validateStock(productQuantities);
        if (!stockErrors.isEmpty()) {
            throw new BusinessException("Stock issues: " + String.join(", ", stockErrors));
        }

        // Build the order
        Order order = new Order();
        order.setCreatedAt(new Date(System.currentTimeMillis()));
        order.setUser(cart.getUser());
        try {
            order.setOrderStatus(orderStatusService.getByStatus("Hazırlanıyor"));
        } catch (BusinessException e) {
            throw new BusinessException("Order status not found");
        }

        // Convert cart items to order items
        List<OrderItem> orderItems = cartMapper.toOrderItems(cart, order);
        order.setOrderItems(orderItems);

        // Decrease stock
        cartItems.forEach(item ->
                productService.decreaseStock(
                        item.getProduct().getId(),
                        item.getQuantity()
                )
        );

        orderRepository.save(order);

        // Clear the user's cart
        cartService.clearCart(cart.getId());

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse getOrderById(UUID orderId) {
        String email = getCurrentUserEmail();

        // Validate email format and order ownership
        orderBusinessRules.validateEmailFormat(email);
        orderBusinessRules.validateOrderOwnership(orderId, email);

        return orderMapper.toOrderResponse(
                orderRepository.findById(orderId)
                        .orElseThrow(() -> new BusinessException("Order not found!"))
        );
    }

    @Override
    public List<OrderResponse> getOrdersByCurrentUser() {
        String email = getCurrentUserEmail();
        return orderRepository.findByUserEmail(email).stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getUserOrders(UUID userId) {
        List<Order> orders = orderRepository.findByUser_IdOrderByCreatedAtDesc(userId);
        return orders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrderStatus(UpdateOrderStatusDto request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new BusinessException("Sipariş bulunamadı."));

        try {
            OrderStatus orderStatus = orderStatusService.getByStatus(request.getNewStatus());
            order.setOrderStatus(orderStatus);
        }
        catch (BusinessException e) {
            throw new BusinessException("Order status not found");
        }


        order.setUpdatedAt(new Date(System.currentTimeMillis()));
        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }


    // Helper method to get the current user's email
    private String getCurrentUserEmail() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        // Check if the user is authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }
        return authentication.getName();
    }
}
