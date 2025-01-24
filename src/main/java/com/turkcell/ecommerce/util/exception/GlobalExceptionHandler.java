package com.turkcell.ecommerce.util.exception;

import com.turkcell.ecommerce.util.exception.result.BusinessExceptionResult;
import com.turkcell.ecommerce.util.exception.result.GeneralExceptionResult;
import com.turkcell.ecommerce.util.exception.result.ValidationExceptionResult;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GeneralExceptionResult handleException(Exception e) {
        return new GeneralExceptionResult("Bilinmeyen bir hata oluştu");
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BusinessExceptionResult handleBusinessException(BusinessException e) {
        return new BusinessExceptionResult(e.getMessage());
    }

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

    @ExceptionHandler({JwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GeneralExceptionResult handleExpiredJwtException(ExpiredJwtException e) {
        return new GeneralExceptionResult("JwtException");
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralExceptionResult handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new GeneralExceptionResult("DataIntegrityViolationException");
    }

}
