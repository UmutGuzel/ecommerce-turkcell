package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.cart.CartDto;
import com.turkcell.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("{cartId}/userId/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable UUID userId, @PathVariable UUID cartId) {
        CartDto cartDto = cartService.getCart(userId, cartId);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }

    @PutMapping("{cartId}/product/{productId}/quantity/{quantity}")
    public ResponseEntity<String> updateCartProduct(@PathVariable UUID cartId, @PathVariable UUID productId, @PathVariable Integer quantity) {
        this.cartService.updateProductQuantityInCart(cartId, productId, quantity);
        return ResponseEntity.ok("Ürün miktarı başarıyla güncellendi.");
    }

    @PostMapping("{cartId}/product/{productId}/quantity/{quantity}")
    public ResponseEntity<String> addProductToCart(@PathVariable UUID cartId, @PathVariable UUID productId, @PathVariable Integer quantity) {
        this.cartService.addProductToCart(cartId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ürün başarıyla sepete eklendi.");
    }

    @DeleteMapping("{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable UUID cartId, @PathVariable UUID productId) {
        String status = cartService.deleteProductFromCart(cartId, productId);
        return ResponseEntity.ok(status);
    }
}
