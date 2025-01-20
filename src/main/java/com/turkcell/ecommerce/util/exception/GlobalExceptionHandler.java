package com.turkcell.ecommerce.util.exception;

import com.turkcell.ecommerce.util.exception.result.GeneralExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler({Exception.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public GeneralExceptionResult handleException(Exception e) {
//        return new GeneralExceptionResult("InternalServerError");
//    }
}
