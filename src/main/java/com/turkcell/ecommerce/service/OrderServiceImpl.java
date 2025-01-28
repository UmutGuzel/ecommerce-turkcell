package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.order.OrderCreateRequest;
import com.turkcell.ecommerce.dto.order.OrderResponse;
import com.turkcell.ecommerce.dto.orderstatus.UpdateOrderStatusDto;
import com.turkcell.ecommerce.entity.*;
import com.turkcell.ecommerce.mapper.CartMapper;
import com.turkcell.ecommerce.mapper.OrderMapper;
import com.turkcell.ecommerce.repository.*;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderStatusRepository orderStatusRepository;

    private final OrderMapper orderMapper;
    private final CartMapper cartMapper;

    OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository, ProductRepository productRepository, OrderStatusRepository orderStatusRepository, OrderMapper orderMapper, CartMapper cartMapper) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderMapper = orderMapper;
        this.cartMapper = cartMapper;
    }


    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public OrderResponse createOrder(OrderCreateRequest request) {
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Stok kontrolü (ne olur ne olmaz diye)
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getDescription());
            }
        }

        // Siparişi oluşturma
        Order order = new Order();
        order.setCreatedAt(new Date(System.currentTimeMillis()));
        order.setUser(cart.getUser());
        order.setOrderStatus(orderStatusRepository.findByStatus("Hazırlanıyor")
                .orElseThrow(() -> new BusinessException("Sipariş durumu bulunamadı.")));

        List<OrderItem> orderItems = cartMapper.toOrderItems(cart, order);

        // Stok güncellemesi
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            product.setStock(product.getStock() - orderItem.getQuantity());
            productRepository.save(product);
        }

        order.setOrderItems(orderItems);
        orderRepository.save(order);

        // Sepeti temizle (cart itemleri silinirse sepet temizlenmiş olur sanırım :D)
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException("Sipariş bulunamadı."));
        return orderMapper.toOrderResponse(order);
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

        OrderStatus orderStatus = orderStatusRepository.findByStatus(request.getNewStatus())
                .orElseThrow(() -> new BusinessException("Uygun olmayan sipariş durumu."));

        order.setOrderStatus(orderStatus);
        order.setUpdatedAt(new Date(System.currentTimeMillis()));
        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }
}
