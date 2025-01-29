package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.product.CreateProductDto;
import com.turkcell.ecommerce.dto.product.DeleteProductDto;
import com.turkcell.ecommerce.dto.product.ProductListingDto;
import com.turkcell.ecommerce.dto.product.UpdateProductDto;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.mapper.ProductMapper;
import com.turkcell.ecommerce.repository.ProductRepository;
import com.turkcell.ecommerce.rules.CategoryBusinessRules;
import com.turkcell.ecommerce.rules.ProductBusinessRules;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CategoryBusinessRules categoryBusinessRules;
    private final ProductMapper productMapper;
    private final ProductBusinessRules productBusinessRules;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, CategoryBusinessRules categoryBusinessRules, ProductMapper productMapper, ProductBusinessRules productBusinessRules) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.categoryBusinessRules = categoryBusinessRules;
        this.productMapper = productMapper;
        this.productBusinessRules = productBusinessRules;
    }

    public void add(CreateProductDto createProductDto) {
        categoryBusinessRules.categoryMustExist(createProductDto.getCategoryId());

        Category category = categoryService
                .findById(createProductDto.getCategoryId())
                .orElse(null);

        productBusinessRules.checkIfProductNameExists(createProductDto.getName());

        Product product = productMapper.toEntity(createProductDto, category);
        productRepository.save(product);
    }

    public void update(UpdateProductDto updateProductDto) {
        Product existingProduct = productBusinessRules.checkIfProductExists(updateProductDto.getId());

        productBusinessRules.checkIfProductNameExists(updateProductDto.getName(), updateProductDto.getId());

        productMapper.updateEntity(updateProductDto, existingProduct);
        productRepository.save(existingProduct);
    }

    @Override
    public void delete(DeleteProductDto deleteProductDto) {
        Product product = productBusinessRules.checkIfProductExists(deleteProductDto.getId());
        productBusinessRules.checkIfProductIsInOrder(product);

        productRepository.delete(product);
    }

    @Override
    public List<ProductListingDto> getAll(String categoryName, BigDecimal minPrice, BigDecimal maxPrice, Boolean inStock, String sortBy, String sortOrder) {
        if (categoryName == null) categoryName = "";
        if (minPrice == null) minPrice = BigDecimal.ZERO;
        if (maxPrice == null) maxPrice = BigDecimal.valueOf(Integer.MAX_VALUE);
        if (inStock == null) inStock = true;
        if (sortBy == null) sortBy = "price";
        if (sortOrder == null) sortOrder = "ASC";

        List<Product> products = productRepository.findProductsWithFilters(categoryName, minPrice, maxPrice, inStock);

        products = productBusinessRules.sortProducts(products, sortBy, sortOrder);

        return productMapper.toProductListingDto(products);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findProductsByCategoryId(UUID categoryId) {
        return Optional.empty();
    }

    @Override
    public boolean isProductExist(UUID categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }


// Order required services
public List<String> validateStock(Map<UUID, Integer> productQuantities) {
    List<UUID> productIds = new ArrayList<>(productQuantities.keySet());
    List<Product> products = productRepository.findAllById(productIds);

    List<String> errors = new ArrayList<>();

    // Check for missing products
    Set<UUID> foundProductIds = products.stream()
            .map(Product::getId)
            .collect(Collectors.toSet());
    productIds.stream()
            .filter(id -> !foundProductIds.contains(id))
            .forEach(id -> errors.add("Product not found: " + id));

    // Check stock for valid products
    products.forEach(product -> {
        int requestedQuantity = productQuantities.get(product.getId());
        if (product.getStock() < requestedQuantity) {
            errors.add("Insufficient stock for product: " + product.getDescription());
        }
    });

    return errors;
}

    @Override
    public void decreaseStock(UUID id, Integer quantity) {
        productRepository.updateStock(id, quantity);
    }

}
