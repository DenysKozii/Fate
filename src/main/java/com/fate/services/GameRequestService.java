package com.fate.services;

import com.fate.dto.GameDto;
import com.fate.dto.GameRequestDto;
import com.fate.pagination.PageDto;

public interface GameRequestService {

    PageDto<GameRequestDto> findAllByUsername(String username, int page, int pageSize);

    GameRequestDto createGameRequest(String username, String friendEmail, Long gamePatternId);

    boolean acceptGameRequest(Long gameRequestId);

    boolean rejectGameRequest(Long gameRequestId);

    GameDto startGameRequest(Long gameRequestId);
}
