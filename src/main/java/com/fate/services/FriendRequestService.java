package com.fate.services;

import com.fate.pagination.PageDto;

import java.util.List;

public interface FriendRequestService {
    boolean inviteByUsername(String username, String friendUsername);

    boolean acceptByUsername(String username, String friendUsername);

    boolean deleteByUsername(String username, String friendUsername);

    PageDto<String> acceptList(String username, int page, int pageSize);

    PageDto<String> inviteList(String username, int page, int pageSize);

}