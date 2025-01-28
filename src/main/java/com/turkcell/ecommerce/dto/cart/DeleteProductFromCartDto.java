package com.turkcell.ecommerce.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteProductFromCartDto {
    @NotNull(message = "Sepet ID'si gereklidir.")
    private UUID cartId;

    @NotNull(message = "Ürün ID'si gereklidir.")
    private UUID productId;
}
