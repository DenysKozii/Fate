package com.fate.controllers;

import com.fate.entity.Role;
import com.fate.entity.User;
import com.fate.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestConnectionController {

    private final UserRepository userRepository;

    @GetMapping("/testConnection")
    public String testConnection() {
        return "connected";
    }

    @GetMapping("/testRegister")
    public Integer firstUser() {
        if(userRepository.findAll().size() == 0) {
            User user = new User();
            user.setUsername("anonimousUser");
            user.setPassword("123456");
            user.setRole(Role.USER);
            userRepository.save(user);
        }
        if(userRepository.findAll().size() == 1) {
            User user = new User();
            user.setUsername("anonimousFriend");
            user.setPassword("123456");
            user.setRole(Role.USER);
            userRepository.save(user);
        }
        return userRepository.findAll().size();
    }

}
