package com.turkcell.ecommerce.util.exception.type;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}