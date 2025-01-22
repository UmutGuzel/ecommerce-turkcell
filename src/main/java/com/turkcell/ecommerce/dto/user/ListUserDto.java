package com.turkcell.ecommerce.dto.user;

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
}
