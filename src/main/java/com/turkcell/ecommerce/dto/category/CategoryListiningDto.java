package com.turkcell.ecommerce.dto.category;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter

public class CategoryListiningDto {
    private UUID id;
    private String name;
    public CategoryListiningDto(UUID id, String name) {
        this.id = id;
        this.name = name;

    }

}
