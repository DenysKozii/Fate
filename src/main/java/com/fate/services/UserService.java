package com.fate.services;

import com.fate.dto.UserDto;
import com.fate.dto.user.LoginRequest;
import com.fate.dto.user.UserProfileDto;
import com.fate.entity.User;
import com.fate.pagination.PageDto;

import java.util.List;

public interface UserService {

    UserProfileDto loginUser(LoginRequest request);

    boolean addUser(UserDto user);

    PageDto<String> getFriendsByUsername(String username, int page, int pageSize);

}
