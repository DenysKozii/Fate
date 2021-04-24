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
    public GamePatternDto newGamePattern(@RequestParam Long orderId,
                                         @RequestParam(defaultValue = "Test") String title,
                                         @RequestParam(defaultValue = "1") Integer usersAmount) {
        return gamePatternService.createGamePattern(orderId, title, usersAmount);
    }

    @GetMapping("/list")
    public PageDto<GamePatternDto> getGamePatternsListByUser(@RequestParam(defaultValue = "0", required = false) int page,
                                                             @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return gamePatternService.getGamePatterns(page, pageSize);
    }

    @DeleteMapping("{gamePatternId}")
    public boolean deleteGamePattern(@PathVariable Long gamePatternId) {
        return gamePatternService.deleteById(gamePatternId);
    }

    @PatchMapping("/available/{gamePatternId}")
    public boolean updateAvailable(@PathVariable Long gamePatternId) {
        return gamePatternService.updateAvailable(gamePatternId);
    }

}
