package com.fate.controllers;


import com.fate.dto.GameRequestDto;
import com.fate.entity.GameRequest;
import com.fate.pagination.PageDto;
import com.fate.services.FriendRequestService;
import com.fate.services.GameRequestService;
import com.fate.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/friend")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final UserService userService;

    @GetMapping("/list")
    public PageDto<String> friends(@RequestParam(defaultValue = "0", required = false) int page,
                                   @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return userService.getFriendsByUsername(page, pageSize);
    }

    @GetMapping("/list/accept")
    public PageDto<String> acceptList(@RequestParam(defaultValue = "0", required = false) int page,
                                      @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return friendRequestService.acceptList(page, pageSize);
    }

    @GetMapping("/list/invite")
    public PageDto<String> inviteList(@RequestParam(defaultValue = "0", required = false) int page,
                                      @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return friendRequestService.inviteList(page, pageSize);
    }

    @PostMapping("/invite")
    public boolean inviteFriend(@RequestParam String friendUsername) {
        return friendRequestService.inviteByUsername(friendUsername);
    }

    @PostMapping("/accept")
    public boolean acceptFriend(@RequestParam String friendUsername) {
        return friendRequestService.acceptByUsername(friendUsername);
    }

    @DeleteMapping
    public boolean deleteFriend(@RequestParam String friendUsername) {
        return friendRequestService.deleteByUsername(friendUsername);
    }

}