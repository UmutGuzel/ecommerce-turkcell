package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImpl implements ProductMapper {
    public Product toEntity(CreateProductDto createProductDto, Category category){
        Product product = new Product();
        product.setName(createProductDto.getName());
        product.setDescription(createProductDto.getDescription());
        product.setPrice(createProductDto.getPrice());
        product.setStock(createProductDto.getStock());
        product.setImage(createProductDto.getImage());
        product.setCategory(category);
        return product;
    }
}
