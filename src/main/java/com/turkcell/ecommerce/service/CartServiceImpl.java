package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.cart.CartItemDto;
import com.turkcell.ecommerce.dto.cart.CartListingDto;
import com.turkcell.ecommerce.entity.Cart;
import com.turkcell.ecommerce.entity.CartItem;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.repository.CartItemRepository;
import com.turkcell.ecommerce.repository.CartRepository;
import com.turkcell.ecommerce.repository.ProductRepository;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addProductToCart(UUID userId, UUID productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Sepet bulunamadı"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Ürün bulunamadı"));

        if (product.getStock() < quantity) {
            throw new BusinessException("Yeterli stok yok");
        }

        CartItem existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingCartItem != null) {
            int newQuantity = existingCartItem.getQuantity() + quantity;
            if (newQuantity > product.getStock()) {
                throw new BusinessException("Sepetteki miktar, stok miktarını aşamaz");
            }
            existingCartItem.setQuantity(newQuantity);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(quantity);
            cartItem.setProduct(product);
            cartItem.setCart(cart);

            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public void removeProductFromCart(UUID userId, UUID productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Sepet bulunamadı"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Ürün bulunamadı"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        } else {
            throw new BusinessException("Sepette böyle bir ürün bulunmamaktadır");
        }
    }

    @Override
    public CartListingDto getCartItems(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Sepet bulunamadı"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        List<CartItemDto> cartItemDtos = cartItems.stream()
                .map(item -> new CartItemDto(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity(),
                        item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                ))
                .toList();

        return new CartListingDto(cartItemDtos);
    }

    @Override
    public void updateProductQuantity(UUID userId, UUID productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Sepet bulunamadı"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Ürün bulunamadı")));

        if (cartItem == null) {
            throw new BusinessException("Sepette ürün bulunamadı");
        }

        if (quantity <= 0) {
            throw new BusinessException("Geçerli bir miktar girilmelidir (0'dan büyük)");
        }

        Product product = cartItem.getProduct();
        if (product.getStock() < quantity) {
            throw new BusinessException("Yeterli stok yok");
        }

        cartItem.setQuantity(quantity);

        cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        cartItemRepository.save(cartItem);
    }
}
