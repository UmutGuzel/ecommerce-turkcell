package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.ListUserDto;
import com.turkcell.ecommerce.dto.user.UpdateUserDto;

import java.util.List;

public interface UserService {
    void add(CreateUserDto createUserDto);
    void update(UpdateUserDto updateUserDto);
    List<ListUserDto> getAll();
}
