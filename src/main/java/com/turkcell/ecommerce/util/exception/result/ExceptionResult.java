package com.turkcell.ecommerce.util.exception.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResult {
    private String type;

    public ExceptionResult() {
    }

    public ExceptionResult(String type) {
        this.type = type;
    }
}
