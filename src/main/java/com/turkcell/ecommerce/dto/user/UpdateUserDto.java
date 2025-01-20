package com.turkcell.ecommerce.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateUserDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private List<UUID> roleIds;
}
