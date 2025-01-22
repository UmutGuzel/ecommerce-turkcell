package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;

import java.util.List;

public interface ProductMapper {
    Product toEntity(CreateProductDto createProductDto, Category category);
    void updateEntity(UpdateProductDto updateProductDto, Product product);
    List<ProductListingDto> toProductListingDto(List<Product> products);
    ProductListingDto toProductListingDto(Product product);
}
