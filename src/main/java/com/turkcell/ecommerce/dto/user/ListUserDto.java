package com.turkcell.ecommerce.dto.user;

import java.util.List;
import java.util.UUID;

public class ListUserDto {
    private String name;
    private String surname;
    private String email;
    private List<UUID> roleIds;
}
