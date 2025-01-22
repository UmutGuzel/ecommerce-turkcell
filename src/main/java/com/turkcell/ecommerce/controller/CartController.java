package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.cart.CartListingDto;
import com.turkcell.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public void addProductToCart(
            @RequestParam UUID userId, @RequestParam UUID productId, @RequestParam int quantity) {
        cartService.addProductToCart(userId, productId, quantity);
    }

    @DeleteMapping("/remove")
    public void removeProductFromCart(@RequestParam UUID userId,
                                                      @RequestParam UUID productId) {
        cartService.removeProductFromCart(userId, productId);
    }

    @GetMapping("/view")
    public ResponseEntity<CartListingDto> viewCart(@RequestParam UUID userId) {
        CartListingDto cartListingDto = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartListingDto);
    }

    @PutMapping("/updateQuantity/{userId}/{productId}")
    public ResponseEntity<Void> updateQuantity(@PathVariable UUID userId,
                                               @PathVariable UUID productId,
                                               @RequestParam int quantity) {
        cartService.updateProductQuantity(userId, productId, quantity);
        return ResponseEntity.ok().build();
    }
}
