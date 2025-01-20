package com.turkcell.ecommerce.util.exception.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationExceptionResult {
    private List<String> errors;

    public ValidationExceptionResult(List<String> errors) {
        this.errors = errors;
    }
}
