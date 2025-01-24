package com.turkcell.ecommerce.dto.cart;

import com.turkcell.ecommerce.dto.product.ProductDto;
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
    private ProductDto product;
    private CartDto cart;
}
