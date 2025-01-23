package com.turkcell.ecommerce.controller;


import com.turkcell.ecommerce.dto.order.OrderCreateRequest;
import com.turkcell.ecommerce.dto.order.OrderResponse;
import com.turkcell.ecommerce.dto.orderstatus.UpdateOrderStatusDto;
import com.turkcell.ecommerce.entity.Order;
import com.turkcell.ecommerce.service.OrderItemService;
import com.turkcell.ecommerce.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Optional<Order> findById(@RequestBody UUID id) {

        return orderService.findById(id);
    }

    // Sipariş oluşturmak için istek
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderCreateRequest request) {
        OrderResponse orderResponse = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    // Kullanıcının siparişlerini listelemek icin istek
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable UUID userId) {
        List<OrderResponse> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    // Sipariş detaylarını alma
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID orderId) {
        OrderResponse orderResponse = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderResponse);
    }

    // Sipariş durumunu güncelleme
    @PutMapping("/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@RequestBody UpdateOrderStatusDto request) {
        OrderResponse orderResponse = orderService.updateOrderStatus(request);
        return ResponseEntity.ok(orderResponse);
    }

}
