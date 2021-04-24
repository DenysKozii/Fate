package com.fate.controllers;

import com.fate.dto.UserDto;
import com.fate.dto.user.LoginRequest;
import com.fate.dto.user.UserProfileDto;
import com.fate.services.AuthorizationService;
import com.fate.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final AuthorizationService authService;


    @PostMapping("/registration")
    public boolean addUser(@RequestBody UserDto user) {
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
