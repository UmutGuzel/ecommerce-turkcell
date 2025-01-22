package com.turkcell.ecommerce.util.exception.result;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class IllegalArgumentExceptionResult extends ExceptionResult{

        private String errorMessage;
        public IllegalArgumentExceptionResult(String errorMessage) {
            super("IllegalArgumentException");
            this.errorMessage = errorMessage;
        }
    }

