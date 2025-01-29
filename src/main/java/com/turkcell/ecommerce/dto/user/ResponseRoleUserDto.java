package com.turkcell.ecommerce.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseRoleUserDto {
    public String email;
    public List<String> roles;
}
