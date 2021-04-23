package com.fate.repositories;

import com.fate.entity.FriendRequest;
import com.fate.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>, PagingAndSortingRepository<FriendRequest, Long> {
    Optional<FriendRequest> findByInvitorUsernameAndAcceptorUsername(String invitorUsername, String acceptorUsername);

    Page<FriendRequest> findAllByAcceptorUsername(String email, Pageable pageable);

    Page<FriendRequest> findAllByInvitorUsername(String email, Pageable pageable);
}
