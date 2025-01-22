package com.turkcell.ecommerce.dto.orderitem;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemListiningDto {
    private UUID id;
    private String name;

    public OrderItemListiningDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
