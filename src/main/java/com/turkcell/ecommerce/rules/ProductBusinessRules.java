package com.turkcell.ecommerce.rules;

import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.repository.ProductRepository;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Component
public class ProductBusinessRules {

    private final ProductRepository productRepository;

    @Autowired
    public ProductBusinessRules(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product checkIfProductExists(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Ürün bulunamadı."));
    }

    public void checkIfProductNameExists(String productName) {
        Product productWithSameName = productRepository.findByName(productName).orElse(null);
        if (productWithSameName != null)
            throw new BusinessException("Aynı isimde başka bir ürün mevcut.");
    }

    public void checkIfProductNameExists(String productName, UUID productId) {
        boolean isDuplicateName = productRepository.existsByNameAndIdNot(productName, productId);
        if (isDuplicateName) {
            throw new BusinessException("Aynı isimde başka bir ürün mevcut.");
        }
    }

    public void checkIfProductIsInOrder(Product product) {
        if (product.getOrderItems() != null && !product.getOrderItems().isEmpty()) {
            throw new BusinessException("Bu ürün, siparişlerle ilişkilendirildiği için silinemez.");
        }
    }

    public List<Product> sortProducts(List<Product> products, String sortBy, String sortOrder) {
        Comparator<Product> comparator;
        if ("price".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Product::getPrice);
        } else if ("name".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Product::getName);
        } else if ("stock".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Product::getStock);
        } else {
            comparator = Comparator.comparing(Product::getPrice);
        }

        if ("DESC".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        products.sort(comparator);
        return products;
    }
}