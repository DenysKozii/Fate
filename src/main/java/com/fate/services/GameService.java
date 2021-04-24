package com.fate.services;


import com.fate.dto.GameDto;
import com.fate.dto.QuestionDto;
import com.fate.pagination.PageDto;

public interface GameService {

    GameDto saveGame(Long gameId);

    PageDto<GameDto> savedGames(int page, int pageSize);

    GameDto loadGame(Long gameId);

    QuestionDto nextQuestion(GameDto game);

    GameDto startNewGame(Long gamePatternId);

}
