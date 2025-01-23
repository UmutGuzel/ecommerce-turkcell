package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.order.OrderResponse;
import com.turkcell.ecommerce.dto.orderitem.OrderItemResponse;
import com.turkcell.ecommerce.entity.Order;
import com.turkcell.ecommerce.entity.OrderItem;

public interface OrderMapper {

    OrderResponse toOrderResponse(Order order);

    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
