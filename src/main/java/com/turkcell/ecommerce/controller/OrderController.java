package com.turkcell.ecommerce.controller;


import com.turkcell.ecommerce.entity.Order;
import com.turkcell.ecommerce.service.OrderItemService;
import com.turkcell.ecommerce.service.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public Optional<Order> findById(@RequestBody UUID id) {

        return orderService.findById(id);
    }

}
