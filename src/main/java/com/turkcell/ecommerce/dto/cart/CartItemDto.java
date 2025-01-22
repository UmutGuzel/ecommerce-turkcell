package com.turkcell.ecommerce.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CartItemDto {
    private UUID productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;

    public CartItemDto(UUID productId, String productName, BigDecimal price, Integer quantity, BigDecimal totalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public void updateTotalPrice() {
        this.totalPrice = this.price.multiply(BigDecimal.valueOf(this.quantity));
    }
}
