package com.fate.repositories;

import com.fate.entity.FriendRequest;
import com.fate.entity.GameRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface GameRequestRepository extends JpaRepository<GameRequest, Long>, PagingAndSortingRepository<GameRequest, Long> {
    Page<GameRequest> findAllByAcceptorUsername(String username, Pageable pageableUnsorted);
}
