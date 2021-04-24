package com.fate.controllers.parameters;

import com.fate.dto.AnswerParameterDto;
import com.fate.pagination.PageDto;
import com.fate.services.AnswerParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answerParameter")
public class AnswerParameterController {

    private final AnswerParameterService answerParameterService;

    @PostMapping("/update/{parameterId}")
    public boolean update(@PathVariable Long parameterId,
                          @RequestParam Integer influence) {
        return answerParameterService.update(parameterId, influence);
    }

    @GetMapping("/list/{answerId}")
    public PageDto<AnswerParameterDto> answerParametersList(@PathVariable Long answerId,
                                                            @RequestParam(defaultValue = "0", required = false) int page,
                                                            @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return answerParameterService.getParametersByAnswerId(answerId, page, pageSize);
    }
}
