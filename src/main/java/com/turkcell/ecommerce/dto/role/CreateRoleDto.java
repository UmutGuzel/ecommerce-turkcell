package com.turkcell.ecommerce.dto.role;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateRoleDto {
    private String name;
    private List<String> permissions;
}
