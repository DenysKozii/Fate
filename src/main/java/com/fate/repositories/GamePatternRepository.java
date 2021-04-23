package com.fate.repositories;

import com.fate.entity.GamePattern;
import com.fate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface GamePatternRepository extends JpaRepository<GamePattern, Long>, PagingAndSortingRepository<GamePattern, Long> {

    Page<GamePattern> findByUsers(User user, Pageable pageable);

    Optional<GamePattern> findById(Long id);

    Optional<GamePattern> findByTitle(String title);
}
