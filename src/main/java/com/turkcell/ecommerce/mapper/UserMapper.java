package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.UpdateUserDto;
import com.turkcell.ecommerce.entity.User;

public interface UserMapper {
    User toEntity(CreateUserDto createUserDto);
    User toEntity(UpdateUserDto updateUserDto);
}
