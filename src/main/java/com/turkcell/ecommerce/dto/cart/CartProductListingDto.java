package com.turkcell.ecommerce.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CartProductListingDto {
    private UUID id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal price;
    private String image;
}