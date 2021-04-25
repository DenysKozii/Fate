package com.fate.controllers.parameters;

import com.fate.dto.ParameterDto;
import com.fate.pagination.PageDto;
import com.fate.services.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gameParameter")
public class GameParameterController {

    private final ParameterService parameterService;

    @DeleteMapping("/{parameterId}")
    public boolean deleteGamePatternParameter(@PathVariable Long parameterId) {
        return parameterService.deleteById(parameterId);
    }

    @PostMapping("/new")
    public boolean newParameter(@RequestBody ParameterDto parameterDto) {
        return parameterService.create(parameterDto);
    }

    @GetMapping("/list/{gamePatternId}")
    public PageDto<ParameterDto> parametersList(@PathVariable Long gamePatternId,
                                                @RequestParam(defaultValue = "0", required = false) int page,
                                                @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return parameterService.getParametersByGamePatternId(gamePatternId, page, pageSize);
    }

}
