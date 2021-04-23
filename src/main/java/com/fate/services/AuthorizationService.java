package com.fate.services;

import com.fate.dto.UserDto;
import com.fate.dto.user.UserProfileDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthorizationService {

    UserProfileDto getProfileOfCurrent();

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

}
