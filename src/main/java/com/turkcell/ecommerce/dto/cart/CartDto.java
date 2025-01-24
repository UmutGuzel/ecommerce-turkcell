package com.turkcell.ecommerce.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CartDto {
    private UUID id;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private List<CartProductListingDto> products = new ArrayList<>();
}
