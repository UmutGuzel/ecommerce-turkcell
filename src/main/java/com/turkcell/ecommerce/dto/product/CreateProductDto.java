package com.turkcell.ecommerce.dto.product;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateProductDto {

    @NotBlank(message = "Ürün adı boş bırakılamaz.")
    @Length(max = 50, message = "Ürün adı 50 karakterden uzun olamaz.")
    private String name;

    @Length(max = 500, message = "Açıklama 500 karakterden uzun olamaz.")
    private String description;

    private String image;

    @NotNull(message = "Fiyat alanı boş bırakılamaz.")
    @Positive(message = "Fiyat 0'dan büyük olmalı.")
    private BigDecimal price;

    @NotNull(message = "Stok alanı boş bırakılamaz.")
    @PositiveOrZero(message = "Stok miktarı 0'dan küçük olamaz.")
    private Integer stock;

    @NotNull(message = "Categori alanı boş bırakılamaz.")
    private UUID categoryId;
}
