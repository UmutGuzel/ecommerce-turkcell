package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.ListUserDto;
import com.turkcell.ecommerce.dto.user.ChangeUserPasswordDto;
import com.turkcell.ecommerce.dto.user.LoginUserDto;

import java.util.List;

public interface UserService {
    void add(CreateUserDto createUserDto);
    void update(ChangeUserPasswordDto changeUserPasswordDto);
    List<ListUserDto> getAll();
    String login(LoginUserDto loginUserDto);
}
