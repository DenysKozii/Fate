package com.fate.services;

import com.fate.dto.GameDto;
import com.fate.dto.GameRequestDto;
import com.fate.pagination.PageDto;

public interface GameRequestService {

    PageDto<GameRequestDto> findAllByUsername(int page, int pageSize);

    GameRequestDto createGameRequest(String friendEmail, Long gamePatternId);

    boolean acceptGameRequest(Long gameRequestId);

    boolean rejectGameRequest(Long gameRequestId);

    GameDto startGameRequest(Long gameRequestId);
}
