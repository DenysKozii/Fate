package com.fate.services.impl;


import com.fate.dto.QuestionDto;
import com.fate.entity.FriendRequest;
import com.fate.entity.Question;
import com.fate.entity.User;
import com.fate.exception.EntityNotFoundException;
import com.fate.mapper.QuestionMapper;
import com.fate.pagination.PageDto;
import com.fate.pagination.PagesUtility;
import com.fate.repositories.FriendRequestRepository;
import com.fate.repositories.UserRepository;
import com.fate.services.FriendRequestService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    @Override
    public boolean inviteByUsername(String username, String friendUsername) {
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findByInvitorUsernameAndAcceptorUsername(username, friendUsername);

        if (!friendRequestOptional.isPresent()) {
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setInvitorUsername(username);
            friendRequest.setAcceptorUsername(friendUsername);
            friendRequest.setStatus(true);
            friendRequestRepository.save(friendRequest);
            return true;
        }
        return false;
    }

    @Override
    public boolean acceptByUsername(String username, String friendUsername) {
        FriendRequest friendRequest = friendRequestRepository.findByInvitorUsernameAndAcceptorUsername(username, friendUsername)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Friend request by user username %s and friend username %s doesn't exists!", username, friendUsername)));
        if (!friendRequest.getStatus())
            return false;
        friendRequest.setStatus(false);
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with username %s not found!", username))
        );
        User friend = userRepository.findByUsername(friendUsername).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with username %s not found!", friendUsername))
        );
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userRepository.save(user);
        userRepository.save(friend);
        friendRequestRepository.save(friendRequest);
        return true;
    }


    @Override
    public boolean deleteByUsername(String username, String friendUsername) {
        FriendRequest friendRequest = friendRequestRepository.findByInvitorUsernameAndAcceptorUsername(username, friendUsername)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Friend request by user username %s and friend username %s doesn't exists!", username, friendUsername)));
        friendRequestRepository.delete(friendRequest);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " doesn't exists!"));
        User friend = userRepository.findByUsername(friendUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + friendUsername + " doesn't exists!"));

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        userRepository.save(user);
        userRepository.save(friend);
        return true;
    }

    @Override
    public PageDto<String> acceptList(String username, int page, int pageSize) {
        Page<FriendRequest> result = friendRequestRepository.findAllByAcceptorUsername(username, PagesUtility.createPageableUnsorted(page, pageSize));
        List<String> content = result.getContent().stream()
                .filter(FriendRequest::getStatus)
                .map(FriendRequest::getInvitorUsername)
                .collect(Collectors.toList());
        return PageDto.of(result.getTotalElements(), page, content);

    }

    @Override
    public PageDto<String> inviteList(String username, int page, int pageSize) {
        Page<FriendRequest> result = friendRequestRepository.findAllByAcceptorUsername(username, PagesUtility.createPageableUnsorted(page, pageSize));
        List<String> content = result.getContent().stream()
                .filter(FriendRequest::getStatus)
                .map(FriendRequest::getAcceptorUsername)
                .collect(Collectors.toList());
        return PageDto.of(result.getTotalElements(), page, content);
    }

}