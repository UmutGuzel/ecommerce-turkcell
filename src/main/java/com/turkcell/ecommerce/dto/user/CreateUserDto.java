package com.turkcell.ecommerce.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Bean;

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
    @Length(min=8)
    @NotEmpty
    private String password;
    private List<UUID> roleIds;

    public @NotEmpty String getName() {
        return name;
    }

    public void setName(@NotEmpty String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public @Email(message = "Eposta uygun formatta değil", regexp = "^[A-Za-z0-9+_.-]+@(.+)$") @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Eposta uygun formatta değil", regexp = "^[A-Za-z0-9+_.-]+@(.+)$") @NotEmpty String email) {
        this.email = email;
    }

    public @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Şifre en az bir büyük harf, bir küçük harf ve bir rakam içermelidir.") @Length(min = 8) @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Şifre en az bir büyük harf, bir küçük harf ve bir rakam içermelidir.") @Length(min = 8) @NotEmpty String password) {
        this.password = password;
    }

    public List<UUID> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<UUID> roleIds) {
        this.roleIds = roleIds;
    }
}
