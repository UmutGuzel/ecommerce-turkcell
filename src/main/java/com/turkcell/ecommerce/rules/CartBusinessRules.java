package com.turkcell.ecommerce.rules;

import com.turkcell.ecommerce.entity.Cart;
import com.turkcell.ecommerce.entity.CartItem;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.repository.CartItemRepository;
import com.turkcell.ecommerce.repository.CartRepository;
import com.turkcell.ecommerce.service.ProductService;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartBusinessRules {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart cartMustExist(UUID cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException("Sepet bulunamadı."));
    }

    public Product productMustExist(UUID productId) {
        return productService.findById(productId)
                .orElseThrow(() -> new BusinessException("Ürün bulunamadı."));
    }

    public void checkIfCartItemExists(UUID cartId, UUID productId, String productName) {
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem != null) {
            throw new BusinessException(productName + " sepette mevcut.");
        }
    }

    public void checkIfProductInStock(Product product) {
        if (product.getStock() == 0) {
            throw new BusinessException(product.getName() + " stok durumundan dolayı sepete eklenemiyor.");
        }
    }

    public void checkIfQuantityExceedsStock(Product product, Integer quantity) {
        if (product.getStock() < quantity) {
            throw new BusinessException("Stok durumundan dolayı " + product.getName()
                    + " ürününden " + product.getStock() + " adetten fazla alamazsınız.");
        }
    }

    public void checkUpdateQuantityIsValid(Integer quantity) {
        if (quantity < 1) {
            throw new BusinessException("Ürün miktarı 1'den küçük olamaz.");
        }
    }

    public void checkIfCartExists(Cart cart) {
        if (cart == null) {
            throw new BusinessException("Sepet bulunamadı.");
        }
    }

    public void checkIfProductInfoExists(CartItem cartItem) {
        if (cartItem.getProduct() == null) {
            throw new BusinessException("Sepette ürün bilgisi eksik.");
        }
    }

    public CartItem checkCartItemExists(UUID cartId, UUID productId) {
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new BusinessException("Sepette belirtilen ürün bulunamadı.");
        }

        return cartItem;
    }
}