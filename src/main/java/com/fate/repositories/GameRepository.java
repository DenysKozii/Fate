package com.fate.repositories;

import com.fate.entity.Game;
import com.fate.entity.GamePattern;
import com.fate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long>, PagingAndSortingRepository<Game, Long> {
    Page<Game> findByUser(User user, Pageable pageable);

    Optional<Game> findById(Long id);

}
