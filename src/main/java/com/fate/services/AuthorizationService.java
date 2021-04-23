package com.fate.services;

import com.fate.dto.UserDto;
import com.fate.dto.user.UserProfileDto;
import com.fate.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthorizationService {

    void authorizeUser(User user);


    UserProfileDto getProfileOfCurrent();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
