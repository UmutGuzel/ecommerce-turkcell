package com.turkcell.ecommerce.dto.user;

import jakarta.validation.constraints.NotEmpty;

public class LoginUserDto {
    @NotEmpty(message = "Email alanı dolu olmalıdır.")
    private String email;
    @NotEmpty(message = "Şifre alanı dolu olmalıdır.")
    private String password;
}
