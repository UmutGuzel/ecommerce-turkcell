package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.ListUserDto;
import com.turkcell.ecommerce.dto.user.ChangeUserPasswordDto;
import com.turkcell.ecommerce.entity.Role;
import com.turkcell.ecommerce.entity.User;

import java.util.List;

public interface UserMapper {
    User toEntity(CreateUserDto createUserDto, List<Role> roles);
    User toEntity(ChangeUserPasswordDto changeUserPasswordDto);
    public List<ListUserDto> toListUserDto(List<User> users);
}
