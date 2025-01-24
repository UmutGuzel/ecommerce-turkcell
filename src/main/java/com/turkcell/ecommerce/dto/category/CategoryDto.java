package com.turkcell.ecommerce.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private UUID id;
    private String name;
    private List<CategoryDto> subCategories;

    public CategoryDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
