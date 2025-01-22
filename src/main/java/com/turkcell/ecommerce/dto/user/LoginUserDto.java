package com.turkcell.ecommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {
    @NotBlank(message = "Email alanı dolu olmalıdır.")
    private String email;
    @NotBlank(message = "Şifre alanı dolu olmalıdır.")
    private String password;
}
