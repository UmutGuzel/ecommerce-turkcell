package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.orderstatus.CreateOrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.OrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.UpdateOrderStatusDto;
import com.turkcell.ecommerce.entity.OrderStatus;
import com.turkcell.ecommerce.mapper.OrderStatusMapper;
import com.turkcell.ecommerce.repository.OrderStatusRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {


    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusMapper orderStatusMapper;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository, OrderStatusMapper orderStatusMapper) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusMapper = orderStatusMapper;
    }


    @Override
    public void add(CreateOrderStatusDto dto) {
        OrderStatus orderStatus = orderStatusMapper.toEntity(dto);
        orderStatus.setCreatedAt(new Date(System.currentTimeMillis()));
        orderStatusRepository.save(orderStatus);
    }

    @Override
    public void update(UpdateOrderStatusDto dto) {
        OrderStatus status = orderStatusRepository.findById(dto.getOrderId()).orElse(null);

        if(status!=null){
            status.setStatus(dto.getNewStatus());
            status.setUpdatedAt(new Date(System.currentTimeMillis()));
            orderStatusRepository.save(status);
        }
        else{
            throw new RuntimeException("Status bulunamadı");
        }

    }

    @Override
    public List<OrderStatusDto> getAll() {
        return orderStatusRepository.findAll().stream().map(orderStatusMapper::toDto).toList();
    }

    @Override
    public OrderStatus getByStatus(String status) {
        return orderStatusRepository.findByStatus(status).orElse(null);
    }

}
