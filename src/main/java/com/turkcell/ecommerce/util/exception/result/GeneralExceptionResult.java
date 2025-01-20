package com.turkcell.ecommerce.util.exception.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class GeneralExceptionResult {
    private String message;
    public GeneralExceptionResult(String message) {
        this.message = message;
    }
}
