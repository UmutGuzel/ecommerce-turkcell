package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public void add(CreateProductDto createProductDto) {


        Category category = categoryService
                .findById(createProductDto.getCategoryId())
                .orElse(null);

        Product product = new Product();
        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());
        product.setStock(createProductDto.getStock());
        product.setImage(createProductDto.getImage());
        product.setCategory(category);


        productRepository.save(product);

    }

    @Override
    public void update(UpdateProductDto updateProductDto) {

    }

    @Override
    public List<ProductListingDto> getAll() {

            List<ProductListingDto> productListingDtos = productRepository
                    .findAll()
                    .stream()
                    .map((product) -> new ProductListingDto(product.getId(), product.getName()))
                    .toList();

            return productListingDtos;

    }
}
