package com.turkcell.ecommerce.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChangeRoleUserDto {
    private String email;
    private List<String> roles;
}
