package com.turkcell.ecommerce.rules;

import com.turkcell.ecommerce.entity.Cart;
import com.turkcell.ecommerce.entity.CartItem;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.repository.CartItemRepository;
import com.turkcell.ecommerce.repository.CartRepository;
import com.turkcell.ecommerce.repository.ProductRepository;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartBusinessRules {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart cartMustExist(UUID cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException("Sepet bulunamadı."));
    }

    public Product productMustExist(UUID productId) {
        return productRepository.findById(productId)
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

    public CartItem cartItemMustExist(UUID cartId, UUID productId) {
        return cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElseThrow(() -> new BusinessException("Sepet ürünü bulunamadı."));
    }

    public Cart cartMustExistForUser(UUID userId, UUID cartId) {
        return cartRepository.findByIdAndUserId(cartId, userId)
                .orElseThrow(() -> new BusinessException("Kullanıcıya ait sepet bulunamadı."));
    }

    public void ensureProductExistsInCartItem(CartItem cartItem) {
        if (cartItem.getProduct() == null) {
            throw new BusinessException("Ürün, sepet öğesinde mevcut değil.");
        }
    }
}