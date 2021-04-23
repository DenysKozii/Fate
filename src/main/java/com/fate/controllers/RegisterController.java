package com.fate.controllers;

import com.fate.dto.UserDto;
import com.fate.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RegisterController {

    private final UserService userService;

    @PostMapping("/registration")
    public boolean addUser(@RequestBody UserDto user) {
        if (!user.getPassword().equals(user.getConfirmPassword()))
            return false;
        return userService.addUser(user);
    }

}
