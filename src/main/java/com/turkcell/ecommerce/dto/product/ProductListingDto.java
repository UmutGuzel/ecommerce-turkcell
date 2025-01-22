package com.turkcell.ecommerce.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductListingDto {

    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String categoryName;
    private String description;

    public ProductListingDto(UUID id, String name, BigDecimal price, Integer stock, String categoryName, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryName = categoryName;
        this.description = description;
    }
}
