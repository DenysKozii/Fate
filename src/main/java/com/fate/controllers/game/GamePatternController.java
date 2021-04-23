package com.fate.controllers.game;

import com.fate.dto.GamePatternDto;
import com.fate.pagination.PageDto;
import com.fate.services.GamePatternService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/gamePattern")
public class GamePatternController {

    private final GamePatternService gamePatternService;

    @PostMapping("/new")
    public GamePatternDto newGamePattern(@RequestParam(defaultValue = "Test") String title,
                                         @RequestParam(defaultValue = "1") Integer usersAmount,
                                         @RequestParam(defaultValue = "anonimousUser") String username) {
        return gamePatternService.createGamePattern(title, usersAmount, username);
    }

    @GetMapping("/list")
    public PageDto<GamePatternDto> getGamePatternsListByUser(@RequestParam(defaultValue = "anonimousUser") String username,
                                                          @RequestParam(defaultValue = "0", required = false) int page,
                                                          @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return gamePatternService.getGamePatternsByUser(username, page, pageSize);
    }

    @DeleteMapping("{gamePatternId}")
    public boolean deleteGamePattern(@PathVariable Long gamePatternId) {
        return gamePatternService.deleteById(gamePatternId);
    }

}
