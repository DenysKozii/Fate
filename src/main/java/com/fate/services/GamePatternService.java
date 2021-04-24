package com.fate.services;


import com.fate.dto.GamePatternDto;
import com.fate.pagination.PageDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GamePatternService {
    GamePatternDto createGamePattern(Long orderId, String title, Integer usersAmount);

    PageDto<GamePatternDto> getGamePatterns(int page, int pageSize);

    boolean deleteById(Long gamePatternId);

    boolean updateAvailable(Long gamePatternId);
}
