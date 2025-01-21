package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;

public interface ProductMapper {
    Product toEntity(CreateProductDto createProductDto, Category category);
}
