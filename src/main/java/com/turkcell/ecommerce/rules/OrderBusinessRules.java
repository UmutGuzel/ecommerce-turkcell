package com.turkcell.ecommerce.rules;

import com.turkcell.ecommerce.entity.Order;
import com.turkcell.ecommerce.entity.User;
import com.turkcell.ecommerce.repository.OrderRepository;
import com.turkcell.ecommerce.repository.UserRepository;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class OrderBusinessRules {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Email format validation
    public void validateEmailFormat(String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new BusinessException("Invalid email format: " + email);
        }
    }

    // Validate order ownership by email
    public void validateOrderOwnership(UUID orderId, String email) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException("Order not found!"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User not found!"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new BusinessException("User " + email + " does not own order " + orderId);
        }
    }

    // Validate order existence
    public void validateOrderExists(UUID orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new BusinessException("Order not found with ID: " + orderId);
        }
    }
}
