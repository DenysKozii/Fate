package com.fate.services;


import com.fate.dto.GamePatternDto;
import com.fate.pagination.PageDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GamePatternService {
    GamePatternDto createGamePattern(String title, Integer usersAmount, String username);

    PageDto<GamePatternDto> getGamePatternsByUser(String username, int page, int pageSize);

    boolean deleteById(Long gamePatternId);
}
