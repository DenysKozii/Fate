package com.fate.services;

import com.fate.dto.UserDto;
import com.fate.entity.User;
import com.fate.pagination.PageDto;

import java.util.List;

public interface UserService {

    boolean addUser(UserDto user);

    PageDto<String> getFriendsByUsername(String username, int page, int pageSize);
}
