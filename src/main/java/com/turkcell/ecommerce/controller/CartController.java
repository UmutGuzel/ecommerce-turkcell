package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.cart.AddProductToCartDto;
import com.turkcell.ecommerce.dto.cart.CartDto;
import com.turkcell.ecommerce.dto.cart.DeleteProductFromCartDto;
import com.turkcell.ecommerce.dto.cart.UpdateCartProductQuantityDto;
import com.turkcell.ecommerce.service.CartService;
import jakarta.validation.Valid;
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

    @GetMapping("/userId/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable UUID userId) {
        CartDto cartDto = cartService.getCart(userId);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateCartProductQuantity(@RequestBody @Valid UpdateCartProductQuantityDto updateCartProductQuantityDto) {
        this.cartService.updateCartProductQuantity(updateCartProductQuantityDto);
        return ResponseEntity.ok("Ürün miktarı başarıyla güncellendi.");
    }

    @PostMapping
    public ResponseEntity<String> addProductToCart(@RequestBody @Valid AddProductToCartDto addProductToCartDto) {
        this.cartService.addProductToCart(addProductToCartDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ürün başarıyla sepete eklendi.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductFromCart(@RequestBody @Valid DeleteProductFromCartDto deleteProductFromCartDto) {
        this.cartService.deleteProductFromCart(deleteProductFromCartDto);
        return ResponseEntity.status(HttpStatus.OK).body("Ürün başarıyla sepetten kaldırıldı.");
    }
}
