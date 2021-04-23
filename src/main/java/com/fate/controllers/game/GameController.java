package com.fate.controllers.game;

import com.fate.dto.GameDto;
import com.fate.dto.GameRequestDto;
import com.fate.pagination.PageDto;
import com.fate.services.AnswerService;
import com.fate.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/game")
public class GameController {

    private final AnswerService answerService;
    private final GameService gameService;

    @GetMapping("/new/{gamePatternId}")
    public GameDto newGame(@PathVariable Long gamePatternId,
                           @RequestParam(defaultValue = "anonimousUser") String username) {
        return gameService.startNewGame(username, gamePatternId);
    }

    @PostMapping("/answer/{gameId}/{answerId}")
    public GameDto checkAnswer(@PathVariable Long gameId,
                               @PathVariable Long answerId) {
        return answerService.answerInfluence(answerId, gameId);
    }

    @PostMapping("/save/{gameId}")
    public GameDto saveGame(@PathVariable Long gameId) {
        return gameService.saveGame(gameId);
    }

    @GetMapping("/saved")
    public PageDto<GameDto> savedGames(@RequestParam(defaultValue = "anonimousUser") String username,
                                       @RequestParam(defaultValue = "0", required = false) int page,
                                       @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return gameService.savedGames(username, page, pageSize);
    }

    @GetMapping("/load/{gameId}")
    public GameDto loadGame(@PathVariable Long gameId) {
        return gameService.loadGame(gameId);
    }

}
