package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.mapper.ProductMapper;
import com.turkcell.ecommerce.mapper.ProductMapperImpl;
import com.turkcell.ecommerce.repository.ProductRepository;
import com.turkcell.ecommerce.rules.CategoryBusinessRules;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CategoryBusinessRules categoryBusinessRules;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, CategoryBusinessRules categoryBusinessRules, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.categoryBusinessRules = categoryBusinessRules;
        this.productMapper = productMapper;
    }

    public void add(CreateProductDto createProductDto) {
        categoryBusinessRules.categoryMustExist(createProductDto.getCategoryId());

        Category category = categoryService
                .findById(createProductDto.getCategoryId())
                .orElse(null);

        Product productWithSameName = productRepository
                .findByName(createProductDto.getName())
                .orElse(null);

        if(productWithSameName != null)
            throw new BusinessException("Product already exists");

        Product product = productMapper.toEntity(createProductDto, category);
        productRepository.save(product);
    }

    public void update(UpdateProductDto updateProductDto) {

    }

    public List<ProductListingDto> getAll() {

            List<ProductListingDto> productListingDtos = productRepository
                    .findAll()
                    .stream()
                    .map((product) -> new ProductListingDto(product.getId(), product.getName()))
                    .toList();

            return productListingDtos;

    }
}
