package com.turkcell.ecommerce.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CartDto {
    private UUID id;
    private List<CartItemDto> cartItems;
    private BigDecimal totalPrice;

    public CartDto(UUID id, List<CartItemDto> cartItems, BigDecimal totalPrice) {
        this.id = id;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    public void updateTotalPrice() {
        this.totalPrice = cartItems.stream()
                .map(CartItemDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
