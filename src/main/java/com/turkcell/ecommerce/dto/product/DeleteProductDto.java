package com.turkcell.ecommerce.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteProductDto {
    @NotNull(message = "Ürün ID'si gereklidir.")
    private UUID id;
}
