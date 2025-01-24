package com.turkcell.ecommerce.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CartItemDto {
    private UUID id;
    private BigDecimal productPrice;
    private Integer quantity;
    private CartProductListingDto product;
    private CartDto cart;
}
