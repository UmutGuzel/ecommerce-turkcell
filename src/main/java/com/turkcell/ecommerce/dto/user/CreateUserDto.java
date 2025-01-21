package com.turkcell.ecommerce.dto.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateUserDto {
    @NotBlank(message = "İsim alanı boş bırakılamaz.")
    private String name;

    @NotBlank(message = "Soyisim alanı boş bırakılamaz.")
    private String surname;

    @Email(message = "Eposta uygun formatta değil", regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @NotBlank(message = "Eposta alanı boş bırakılamaz.")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="Şifre en az bir büyük harf, bir küçük harf ve bir rakam içermelidir.")
    @Length(min=8)
    @NotBlank
    private String password;
    //private List<UUID> roleIds;
}
