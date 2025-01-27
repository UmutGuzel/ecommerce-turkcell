package com.turkcell.ecommerce.controller;

import com.turkcell.ecommerce.dto.user.ChangeUserPasswordDto;
import com.turkcell.ecommerce.dto.user.CreateUserDto;
import com.turkcell.ecommerce.dto.user.ListUserDto;
import com.turkcell.ecommerce.dto.user.LoginUserDto;
import com.turkcell.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
   UserService userService;
   public UserController(UserService userService) {
      this.userService = userService;
   }

    @GetMapping("/all")
    public List<ListUserDto> getAll() {
        return userService.getAll();
    }

   @PostMapping("/register")
    public void add(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.add(createUserDto);
    }

    @PutMapping("/change-password")
    public void changePassword(@RequestBody @Valid ChangeUserPasswordDto changeUserPasswordDto) {
        userService.update(changeUserPasswordDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginUserDto loginUserDto) {
        return userService.login(loginUserDto);
    }

}
