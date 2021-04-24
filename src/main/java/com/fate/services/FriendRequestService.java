package com.fate.services;

import com.fate.pagination.PageDto;

import java.util.List;

public interface FriendRequestService {
    boolean inviteByUsername(String friendUsername);

    boolean acceptByUsername(String friendUsername);

    boolean deleteByUsername(String friendUsername);

    PageDto<String> acceptList(int page, int pageSize);

    PageDto<String> inviteList(int page, int pageSize);

}