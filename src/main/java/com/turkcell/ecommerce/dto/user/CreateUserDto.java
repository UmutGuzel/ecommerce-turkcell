package com.turkcell.ecommerce.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateUserDto {
    @NotEmpty
    private String name;
    private String surname;
    @Email(message = "Eposta uygun formatta değil", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @NotEmpty
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="Şifre en az bir büyük harf, bir küçük harf ve bir rakam içermelidir.")
    @Length(min=8, message = "Şifre en az 8 karakter olmalıdır.")
    @NotEmpty(message = "Şifre boş olamaz")
    private String password;
    private List<UUID> roleIds;
}
