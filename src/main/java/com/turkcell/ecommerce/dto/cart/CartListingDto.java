package com.turkcell.ecommerce.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartListingDto {
    private List<CartItemDto> items;

    public CartListingDto(List<CartItemDto> items) {
        this.items = items;
    }
}
