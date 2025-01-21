package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.entity.Order;
import com.turkcell.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }
}
