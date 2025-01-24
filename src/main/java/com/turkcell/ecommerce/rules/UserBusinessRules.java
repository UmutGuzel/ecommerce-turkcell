package com.turkcell.ecommerce.rules;

import com.turkcell.ecommerce.dto.user.ChangeUserPasswordDto;
import com.turkcell.ecommerce.dto.user.LoginUserDto;
import com.turkcell.ecommerce.entity.Role;
import com.turkcell.ecommerce.entity.User;
import com.turkcell.ecommerce.repository.UserRepository;
import com.turkcell.ecommerce.util.exception.type.BusinessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserBusinessRules {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserBusinessRules() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public void ValidateUser(Optional<User> user)
    {
        user.ifPresent(u -> {
            throw new BusinessException("Kullanici zaten var");
        });

    }
    public void ValidateUser(Optional<User> user, ChangeUserPasswordDto changeUserPasswordDto)
    {
        user.orElseThrow(() -> new BusinessException("Kullanıcı bulunamadı"));

        if(!bCryptPasswordEncoder.matches(changeUserPasswordDto.getOldPassword(), user.get().getPassword()))
            throw new BusinessException("Eski şifre hatalı");

    }

    public void ValidateUser(Optional<User> user, LoginUserDto loginUserDto)
    {
        user.orElseThrow(() -> new BusinessException("Kullanıcı bulunamadı"));

        if(!bCryptPasswordEncoder.matches(loginUserDto.getPassword(), user.get().getPassword()))
            throw new BusinessException("Şifre hatalı");

    }

    public void ValidateRoles(List<Role> roles)
    {
        if(roles.isEmpty())
            throw new BusinessException("Rol bulunamadı");
        if(roles.stream().filter(role -> role.getName().equals("admin")).count() > 0)
            throw new BusinessException("Admin rolü atanamaz");
    }
}
