package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.orderstatus.CreateOrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.OrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.UpdateOrderStatusDto;

import java.util.List;
import java.util.UUID;

public interface OrderStatusService {

    void add(CreateOrderStatusDto dto);

    void update(UpdateOrderStatusDto dto);

    List<OrderStatusDto> getAll();
}
