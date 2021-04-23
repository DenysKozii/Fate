package com.fate.repositories;

import com.fate.entity.GamePattern;
import com.fate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Page<User> findFriendsByUsername(String username, Pageable pageable);

    List<User> findAllByGamePatterns(GamePattern gamePattern);

}
