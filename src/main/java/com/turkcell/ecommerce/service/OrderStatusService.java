package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.orderstatus.CreateOrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.OrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.UpdateOrderStatusDto;
import com.turkcell.ecommerce.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderStatusService {

    void add(CreateOrderStatusDto dto);

    void update(UpdateOrderStatusDto dto);

    List<OrderStatusDto> getAll();

    OrderStatus getByStatus(String status);
}
