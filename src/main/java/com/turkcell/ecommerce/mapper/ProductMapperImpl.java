package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void updateEntity(UpdateProductDto updateProductDto, Product existingProduct) {
        if (updateProductDto.getName() != null && !updateProductDto.getName().isBlank()) {
            existingProduct.setName(updateProductDto.getName());
        }
        if (updateProductDto.getPrice() != null) {
            existingProduct.setPrice(updateProductDto.getPrice());
        }
        if (updateProductDto.getStock() != null) {
            existingProduct.setStock(updateProductDto.getStock());
        }
    }

    @Override
    public List<ProductListingDto> toProductListingDto(List<Product> products) {
        return products.stream()
                .map(this::toProductListingDto)
                .toList();
    }

    @Override
    public ProductListingDto toProductListingDto(Product product) {
        return new ProductListingDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getName(),
                product.getDescription()
        );
    }
}
