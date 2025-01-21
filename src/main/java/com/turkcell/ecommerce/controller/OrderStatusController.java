package com.turkcell.ecommerce.controller;


import com.turkcell.ecommerce.dto.orderstatus.CreateOrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.OrderStatusDto;
import com.turkcell.ecommerce.dto.orderstatus.UpdateOrderStatusDto;
import com.turkcell.ecommerce.service.OrderStatusService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderStatus")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @PostMapping
    public void add(@RequestBody @Valid CreateOrderStatusDto createOrderStatusDto) {
        this.orderStatusService.add(createOrderStatusDto);
    }

    @PutMapping
    public void update(@RequestBody @Valid UpdateOrderStatusDto updateOrderStatusDto) {
        this.orderStatusService.update(updateOrderStatusDto);
    }

    @GetMapping
    public List<OrderStatusDto> getAll() {
        return this.orderStatusService.getAll();
    }
}
