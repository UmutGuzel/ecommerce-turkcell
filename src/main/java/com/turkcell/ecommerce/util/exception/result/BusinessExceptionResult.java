package com.turkcell.ecommerce.util.exception.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessExceptionResult extends ExceptionResult {
    private String errorMessage;
    public BusinessExceptionResult(String errorMessage) {
        super("BusinessException");
        this.errorMessage = errorMessage;
    }
}
