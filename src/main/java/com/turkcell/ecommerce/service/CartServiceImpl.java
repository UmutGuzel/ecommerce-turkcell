package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.cart.CartDto;
import com.turkcell.ecommerce.dto.cart.CartProductListingDto;
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
    private UserService userService;

    @Autowired
    public CartServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDto addProductToCart(UUID cartId, UUID productId, Integer quantity) {
        Cart cart = cartBusinessRules.cartMustExist(cartId);
        Product product = cartBusinessRules.productMustExist(productId);

        cartBusinessRules.checkIfCartItemExists(cartId, productId, product.getName());
        cartBusinessRules.checkIfProductInStock(product);
        cartBusinessRules.checkIfQuantityExceedsStock(product, quantity);

        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setProductPrice(product.getPrice());

        cartItemRepository.save(newCartItem);

        cart.setTotalPrice(cart.getTotalPrice().add(product.getPrice().multiply(BigDecimal.valueOf(quantity))));

        cartRepository.save(cart);

        CartDto cartDTO = modelMapper.map(cart, CartDto.class);

        List<CartProductListingDto> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), CartProductListingDto.class)).toList();

        cartDTO.setProducts(products);

        return cartDTO;
    }

    @Override
    public CartDto getCart(UUID userId) {
//        Cart cart = cartRepository.findCartByUserIdAndCartId(userId, cartId);
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new BusinessException("Cart not found"));
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
    public CartDto createCart(UUID userId) {
        User user= userService.findById(userId).orElseThrow(() -> new BusinessException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto updateProductQuantityInCart(UUID cartId, UUID productId, Integer quantity) {
        cartBusinessRules.checkUpdateQuantityIsValid(quantity);
        Cart cart = cartBusinessRules.cartMustExist(cartId);
        Product product = cartBusinessRules.productMustExist(productId);

        cartBusinessRules.checkIfProductInStock(product);
        cartBusinessRules.checkIfQuantityExceedsStock(product, quantity);

        CartItem cartItem = cartBusinessRules.checkCartItemExists(cartId, productId);

        BigDecimal currentProductTotalPrice = cartItem.getProductPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        BigDecimal cartPrice = cart.getTotalPrice().subtract(currentProductTotalPrice);

        cartItem.setProductPrice(product.getPrice());
        cartItem.setQuantity(quantity);

        BigDecimal updatedProductTotalPrice = cartItem.getProductPrice()
                .multiply(BigDecimal.valueOf(quantity));
        cart.setTotalPrice(cartPrice.add(updatedProductTotalPrice));

        cartItem = cartItemRepository.save(cartItem);

        CartDto cartDTO = modelMapper.map(cart, CartDto.class);

        List<CartProductListingDto> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), CartProductListingDto.class)).toList();

        cartDTO.setProducts(products);

        return cartDTO;
    }


    @Override
    public String deleteProductFromCart(UUID cartId, UUID productId) {
        Cart cart = cartBusinessRules.cartMustExist(cartId);

        CartItem cartItem = cartBusinessRules.checkCartItemExists(cartId, productId);

        BigDecimal productTotalPrice = cartItem.getProductPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        BigDecimal updatedCartPrice = cart.getTotalPrice().subtract(productTotalPrice);
        cart.setTotalPrice(updatedCartPrice);

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return cartItem.getProduct().getName() + " başarıyla sepetten kaldırıldı.";
    }
}
