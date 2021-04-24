package com.fate.controllers;

import com.fate.dto.UserDto;
import com.fate.dto.user.LoginRequest;
import com.fate.dto.user.UserProfileDto;
import com.fate.services.AuthorizationService;
import com.fate.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final AuthorizationService authService;


    @PostMapping("/registration")
    public boolean registration(@RequestBody UserDto user) {
        if (!user.getPassword().equals(user.getConfirmPassword()))
            return false;
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public UserProfileDto login(@RequestBody @Valid LoginRequest request) {
        return userService.loginUser(request);
    }

    @GetMapping("/profile")
    public UserProfileDto profile() {
        return authService.getProfileOfCurrent();
    }

}
