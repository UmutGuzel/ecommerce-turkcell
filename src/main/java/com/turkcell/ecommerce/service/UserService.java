package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.user.*;
import com.turkcell.ecommerce.entity.Category;
import com.turkcell.ecommerce.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    void add(CreateUserDto createUserDto);
    void update(ChangeUserPasswordDto changeUserPasswordDto);
    ResponseRoleUserDto changeRole(ChangeRoleUserDto changeRoleUserDto) ;
    List<ListUserDto> getAll();
    String login(LoginUserDto loginUserDto);
    Optional<User> findById(UUID id);

}
