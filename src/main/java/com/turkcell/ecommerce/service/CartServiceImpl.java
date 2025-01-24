package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.cart.CartDto;
import com.turkcell.ecommerce.dto.cart.CartProductListingDto;
import com.turkcell.ecommerce.entity.Cart;
import com.turkcell.ecommerce.entity.CartItem;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.repository.CartItemRepository;
import com.turkcell.ecommerce.repository.CartRepository;
import com.turkcell.ecommerce.repository.ProductRepository;
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
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CartServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDto addProductToCart(UUID cartId, UUID productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException("Sepet bulunamadı."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Ürün bulunamadı."));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem != null) {
            throw new BusinessException(product.getName() + " sepette mevcut.");
        }

        if (product.getStock() == 0) {
            throw new BusinessException(product.getName() + " stok durumundan dolayı sepete eklenemiyor.");
        }

        if (product.getStock() < quantity) {
            throw new BusinessException("Stok durumundan dolayı" + product.getName()
                    + " ürününden " + product.getStock() + " adetten fazla alamazsınız.");
        }

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
    public CartDto getCart(UUID userId, UUID cartId) {
        Cart cart = cartRepository.findCartByUserIdAndCartId(userId, cartId);

        if (cart == null) {
            throw new BusinessException("Sepet bulunamadı.");
        }

        CartDto cartDTO = modelMapper.map(cart, CartDto.class);

        List<CartProductListingDto> products = cart.getCartItems().stream()
                .map(cartItem -> {
                    if (cartItem.getProduct() == null) {
                        throw new BusinessException("Sepette ürün bilgisi eksik.");
                    }
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
    public CartDto updateProductQuantityInCart(UUID cartId, UUID productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException("Sepet bulunamadı."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Ürün bulunamadı."));

        if (product.getStock() == 0) {
            throw new BusinessException(product.getName() + " stok durumundan dolayı sepete eklenemiyor.");
        }

        if (product.getStock() < quantity) {
            throw new BusinessException("Stok durumundan dolayı" + product.getName()
                    + " ürününden" + product.getStock() + " adetten fazla alamazsınız.");
        }

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new BusinessException("Ürün " + product.getName() + " sepette bulunmamaktadır.");
        }

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
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException("Sepet bulunamadı"));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new BusinessException("Sepette belirtilen ürün bulunamadı.");
        }

        BigDecimal productTotalPrice = cartItem.getProductPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        BigDecimal updatedCartPrice = cart.getTotalPrice().subtract(productTotalPrice);
        cart.setTotalPrice(updatedCartPrice);

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return cartItem.getProduct().getName() + " başarıyla sepetten kaldırıldı.";
    }
}
