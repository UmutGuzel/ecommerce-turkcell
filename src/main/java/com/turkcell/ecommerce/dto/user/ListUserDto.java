package com.turkcell.ecommerce.dto.user;

import com.turkcell.ecommerce.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ListUserDto {
    private String name;
    private String surname;
    private String email;
    private List<String> roles;
}
