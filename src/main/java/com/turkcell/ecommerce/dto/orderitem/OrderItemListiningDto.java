package com.turkcell.ecommerce.dto.orderitem;

import java.util.UUID;

public class OrderItemListiningDto {
    private UUID id;
    private String name;

    public OrderItemListiningDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}