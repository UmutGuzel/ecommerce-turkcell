package com.turkcell.ecommerce.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCategoryDto {

    @NotBlank(message = "Kategori adı boş olamaz.")
    @Size(max=100,message = "Kategori adı en fazla 100 karakter olmalıdır.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Kategori adı boş olamaz.") @Size(max = 100, message = "Kategori adı en fazla 100 karakter olmalıdır.") String name) {
        this.name = name;

    }
}
