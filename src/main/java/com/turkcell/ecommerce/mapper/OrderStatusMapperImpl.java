package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.orderstatus.CreateOrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.OrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.UpdateOrderStatusDto;
import com.turkcell.ecommerce.entity.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusMapperImpl implements OrderStatusMapper {

    public OrderStatus toEntity(CreateOrderStatusDto dto) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatus(dto.getStatus());
        return orderStatus;
    }

    public OrderStatus toEntity(UpdateOrderStatusDto dto) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(dto.getOrderId());
        orderStatus.setStatus(dto.getNewStatus());
        return orderStatus;
    }

    public OrderStatusDto toDto(OrderStatus orderStatus) {
        OrderStatusDto orderStatusDto = new OrderStatusDto();
        orderStatusDto.setId(orderStatus.getId());
        orderStatusDto.setStatus(orderStatus.getStatus());
        return orderStatusDto;
    }

}
