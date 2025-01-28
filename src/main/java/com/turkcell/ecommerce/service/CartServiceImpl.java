package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.cart.*;
import com.turkcell.ecommerce.entity.Cart;
import com.turkcell.ecommerce.entity.CartItem;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.entity.User;
import com.turkcell.ecommerce.repository.CartItemRepository;
import com.turkcell.ecommerce.repository.CartRepository;
import com.turkcell.ecommerce.repository.ProductRepository;
import com.turkcell.ecommerce.rules.CartBusinessRules;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartBusinessRules cartBusinessRules;

    private final ModelMapper modelMapper;

    @Autowired
    public CartServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void addProductToCart(AddProductToCartDto addProductToCartDto) {
        Cart cart = cartBusinessRules.cartMustExist(addProductToCartDto.getCartId());
        Product product = cartBusinessRules.productMustExist(addProductToCartDto.getProductId());

        cartBusinessRules.checkIfCartItemExists(addProductToCartDto.getCartId(), addProductToCartDto.getProductId(), product.getName());
        cartBusinessRules.checkIfProductInStock(product);
        cartBusinessRules.checkIfQuantityExceedsStock(product, addProductToCartDto.getQuantity());

        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(addProductToCartDto.getQuantity());
        newCartItem.setProductPrice(product.getPrice());

        cartItemRepository.save(newCartItem);

        cart.setTotalPrice(cart.getTotalPrice().add(product.getPrice().multiply(BigDecimal.valueOf(addProductToCartDto.getQuantity()))));

        cartRepository.save(cart);

        CartDto cartDTO = modelMapper.map(cart, CartDto.class);

        List<CartProductListingDto> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), CartProductListingDto.class)).toList();

        cartDTO.setProducts(products);
    }

    @Override
    public CartDto getCart(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId);
        cartBusinessRules.checkIfCartExists(cart);

        CartDto cartDTO = modelMapper.map(cart, CartDto.class);

        List<CartProductListingDto> products = cart.getCartItems().stream()
                .map(cartItem -> {
                    cartBusinessRules.checkIfProductInfoExists(cartItem);

                    CartProductListingDto productDto = modelMapper.map(cartItem.getProduct(), CartProductListingDto.class);
                    productDto.setQuantity(cartItem.getQuantity());

                    BigDecimal unitPrice = cartItem.getProductPrice();
                    productDto.setUnitPrice(unitPrice);

                    BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                    productDto.setPrice(totalPrice);
                    return productDto;
                })
                .collect(Collectors.toList());

        cartDTO.setProducts(products);

        return cartDTO;
    }

    @Override
    public void createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public void updateCartProductQuantity(UpdateCartProductQuantityDto updateCartProductQuantityDto) {
        Cart cart = cartBusinessRules.cartMustExist(updateCartProductQuantityDto.getCartId());
        Product product = cartBusinessRules.productMustExist(updateCartProductQuantityDto.getProductId());
        cartBusinessRules.checkUpdateQuantityIsValid(updateCartProductQuantityDto.getQuantity());

        cartBusinessRules.checkIfProductInStock(product);
        cartBusinessRules.checkIfQuantityExceedsStock(product, updateCartProductQuantityDto.getQuantity());

        CartItem cartItem = cartBusinessRules.checkCartItemExists(updateCartProductQuantityDto.getCartId(), updateCartProductQuantityDto.getProductId());

        BigDecimal currentProductTotalPrice = cartItem.getProductPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        BigDecimal cartPrice = cart.getTotalPrice().subtract(currentProductTotalPrice);

        cartItem.setProductPrice(product.getPrice());
        cartItem.setQuantity(updateCartProductQuantityDto.getQuantity());

        BigDecimal updatedProductTotalPrice = cartItem.getProductPrice()
                .multiply(BigDecimal.valueOf(updateCartProductQuantityDto.getQuantity()));
        cart.setTotalPrice(cartPrice.add(updatedProductTotalPrice));

        cartItemRepository.save(cartItem);

        CartDto cartDTO = modelMapper.map(cart, CartDto.class);

        List<CartProductListingDto> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), CartProductListingDto.class)).toList();

        cartDTO.setProducts(products);
    }

    @Override
    public void deleteProductFromCart(DeleteProductFromCartDto deleteProductFromCartDto) {
        Cart cart = cartBusinessRules.cartMustExist(deleteProductFromCartDto.getCartId());
        cartBusinessRules.productMustExist(deleteProductFromCartDto.getProductId());

        CartItem cartItem = cartBusinessRules.checkCartItemExists(deleteProductFromCartDto.getCartId(), deleteProductFromCartDto.getProductId());

        BigDecimal productTotalPrice = cartItem.getProductPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        BigDecimal updatedCartPrice = cart.getTotalPrice().subtract(productTotalPrice);
        cart.setTotalPrice(updatedCartPrice);

        cartItemRepository.deleteCartItemByProductIdAndCartId(deleteProductFromCartDto.getCartId(), deleteProductFromCartDto.getProductId());
    }
}
