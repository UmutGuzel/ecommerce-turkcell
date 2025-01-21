package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.orderitem.CreateOrderItemDto;
import com.turkcell.ecommerce.dto.orderitem.OrderItemListiningDto;
import com.turkcell.ecommerce.dto.orderitem.UpdateOrderItemDto;
import com.turkcell.ecommerce.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    void add(CreateOrderItemDto createOrderItemDto);
    void update(UpdateOrderItemDto updateOrderItemDto);
    List<OrderItemListiningDto> getAll();
}
