package com.turkcell.ecommerce.mapper;

import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.ListUserDto;
import com.turkcell.ecommerce.dto.user.ChangeUserPasswordDto;
import com.turkcell.ecommerce.entity.Role;
import com.turkcell.ecommerce.entity.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;


@Service
public class UserMapperImpl implements UserMapper {
        public User toEntity(CreateUserDto createUserDto, List<Role> roles) {
            User user = new User();
            user.setName(createUserDto.getName());
            user.setSurname(createUserDto.getSurname());
            user.setEmail(createUserDto.getEmail());
            user.setPassword(createUserDto.getPassword());
            user.setCreatedAt(new Date(System.currentTimeMillis()));
            user.setUpdatedAt(new Date(System.currentTimeMillis()));
            user.setRoles(roles);
            return user;
        }

    @Override
    public User toEntity(CreateUserDto createUserDto, Role role) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setSurname(createUserDto.getSurname());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        user.setRoles(List.of(role));
        return user;
    }

    public User toEntity(ChangeUserPasswordDto changeUserPasswordDto) {
            User user = new User();
            user.setName(changeUserPasswordDto.getName());
            user.setEmail(changeUserPasswordDto.getEmail());
            user.setPassword(changeUserPasswordDto.getNewPassword());
            return user;
        }

        public List<ListUserDto> toListUserDto(List<User> users) {
            return users.stream().map(user -> {
                ListUserDto listUserDto = new ListUserDto();
                listUserDto.setName(user.getName());
                listUserDto.setSurname(user.getSurname());
                listUserDto.setEmail(user.getEmail());
                return listUserDto;
            }).toList();
        }
    }