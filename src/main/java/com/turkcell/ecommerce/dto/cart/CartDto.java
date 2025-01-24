package com.turkcell.ecommerce.dto.cart;

import com.turkcell.ecommerce.dto.product.ProductDto;
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
    private BigDecimal totalPrice = new BigDecimal("0.0");
    private List<ProductDto> products = new ArrayList<>();
}
