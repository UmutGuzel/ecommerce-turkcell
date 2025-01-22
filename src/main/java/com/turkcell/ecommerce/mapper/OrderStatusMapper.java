package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.orderstatus.CreateOrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.OrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.UpdateOrderStatusDto;
import com.turkcell.ecommerce.entity.OrderStatus;

public interface OrderStatusMapper {

    OrderStatus toEntity(CreateOrderStatusDto createOrderStatusDto);

    OrderStatus toEntity(UpdateOrderStatusDto updateOrderStatusDto);

    OrderStatusDto toDto(OrderStatus orderStatus);
}
