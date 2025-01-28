package com.turkcell.ecommerce.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddProductToCartDto {
    @NotNull(message = "Sepet ID'si gereklidir.")
    private UUID cartId;

    @NotNull(message = "Ürün ID'si gereklidir.")
    private UUID productId;

    @Positive(message = "Miktar 0'dan büyük olmalı.")
    private Integer quantity;
}
