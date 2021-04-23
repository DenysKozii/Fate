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
    public PageDto<String> friends(@RequestParam(defaultValue = "anonimousUser") String username,
                                @RequestParam(defaultValue = "0", required = false) int page,
                                @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return userService.getFriendsByUsername(username, page, pageSize);
    }

    @GetMapping("/list/accept")
    public PageDto<String> acceptList(@RequestParam(defaultValue = "anonimousUser") String username,
                                   @RequestParam(defaultValue = "0", required = false) int page,
                                   @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return friendRequestService.acceptList(username, page, pageSize);
    }

    @GetMapping("/list/invite")
    public PageDto<String> inviteList(@RequestParam(defaultValue = "anonimousUser") String username,
                                   @RequestParam(defaultValue = "0", required = false) int page,
                                   @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return friendRequestService.inviteList(username, page, pageSize);
    }

    @PostMapping("/invite")
    public boolean inviteFriend(@RequestParam(defaultValue = "anonimousUser") String username,
                                @RequestParam String friendUsername) {
        return friendRequestService.inviteByUsername(username, friendUsername);
    }

    @PostMapping("/accept")
    public boolean acceptFriend(@RequestParam(defaultValue = "anonimousUser") String username,
                                @RequestParam String friendUsername) {
        return friendRequestService.acceptByUsername(username, friendUsername);
    }

    @DeleteMapping
    public boolean deleteFriend(@RequestParam(defaultValue = "anonimousUser") String username,
                                @RequestParam String friendUsername) {
        return friendRequestService.deleteByUsername(username, friendUsername);
    }

}