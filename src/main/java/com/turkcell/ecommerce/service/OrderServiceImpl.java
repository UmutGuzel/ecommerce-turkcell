package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.entity.Cart;
import com.turkcell.ecommerce.entity.CartItem;
import com.turkcell.ecommerce.entity.Order;
import com.turkcell.ecommerce.entity.OrderItem;
import com.turkcell.ecommerce.repository.OrderItemRepository;
import com.turkcell.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }


    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public void createOrder(Cart cart) {
        if (cart == null){
            throw new RuntimeException("Sepet bulunamadı");
        }
        if(cart.getCartItems() == null || cart.getCartItems().isEmpty()){
            throw new RuntimeException("Sepet boş olamaz");
        }

        Order order = new Order();
        List<CartItem> cartItems = cart.getCartItems();

        cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                    orderItem.setOrder(order);
                    orderItem.setStatus("Hazırlanıyor");
                    return orderItem;
                })
                .forEach(orderItemRepository::save);

        order.setCreatedAt(new Date(System.currentTimeMillis()));
        orderRepository.save(order);

    }

}
