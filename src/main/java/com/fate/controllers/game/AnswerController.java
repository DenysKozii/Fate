package com.fate.controllers.game;

import com.fate.dto.AnswerDto;
import com.fate.pagination.PageDto;
import com.fate.services.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/list/{questionId}")
    public PageDto<AnswerDto> answersList(@PathVariable Long questionId,
                                          @RequestParam(defaultValue = "0", required = false) int page,
                                          @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return answerService.getAnswersByQuestionId(questionId, page, pageSize);
    }

    @PostMapping("/new/{questionId}")
    public AnswerDto newAnswerByQuestion(@PathVariable Long questionId,
                                         @RequestParam String context) {
        return answerService.createNewAnswer(questionId, context);
    }

    @DeleteMapping("/{answerId}")
    public boolean deleteAnswer(@PathVariable Long answerId) {
        return answerService.deleteById(answerId);
    }

}
