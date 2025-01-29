package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.order.OrderCreateRequest;
import com.turkcell.ecommerce.dto.order.OrderResponse;
import com.turkcell.ecommerce.dto.orderstatus.UpdateOrderStatusDto;
import com.turkcell.ecommerce.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Optional<Order> findById(UUID id);

    OrderResponse createOrder(OrderCreateRequest request);
    OrderResponse getOrderById(UUID orderId);

    List<OrderResponse> getOrdersByCurrentUser();

    List<OrderResponse> getUserOrders(UUID userId);
    OrderResponse updateOrderStatus(UpdateOrderStatusDto request);
}
