package com.turkcell.ecommerce.dto.product;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class UpdateProductDto {
    @NotNull(message = "Ürün ID'si gereklidir.")
    private UUID id;

    @Length(max = 50, message = "Ürün adı 50 karakterden uzun olamaz.")
    private String name;

    @Positive(message = "Fiyat 0'dan büyük olmalı.")
    private BigDecimal price;

    @PositiveOrZero(message = "Stok miktarı 0'dan küçük olamaz.")
    private Integer stock;
}
