package com.turkcell.ecommerce.util.exception;

import com.turkcell.ecommerce.util.exception.result.GeneralExceptionResult;
import com.turkcell.ecommerce.util.exception.result.ValidationExceptionResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
@ExceptionHandler({MethodArgumentNotValidException.class})
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ValidationExceptionResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    return new ValidationExceptionResult(e
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map((error) -> error.getDefaultMessage())
            .toList());
}
}
