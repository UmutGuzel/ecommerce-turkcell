package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.orderitem.CreateOrderItemDto;
import com.turkcell.ecommerce.dto.orderitem.OrderItemListiningDto;
import com.turkcell.ecommerce.dto.orderitem.UpdateOrderItemDto;
import com.turkcell.ecommerce.service.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderItems")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public void add(@RequestBody @Valid CreateOrderItemDto createOrderItemDto)
    {
        this.orderItemService.add(createOrderItemDto);
    }

    @GetMapping
    public List<OrderItemListiningDto> getAll() {
        return this.orderItemService.getAll();
    }

    @PutMapping
    public void update(@RequestBody UpdateOrderItemDto updateProductDto)
    {
        this.orderItemService.update(updateProductDto);
    }



}
