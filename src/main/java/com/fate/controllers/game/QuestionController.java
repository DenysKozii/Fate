package com.fate.controllers.game;

import com.fate.dto.QuestionDto;
import com.fate.pagination.PageDto;
import com.fate.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;


    @PostMapping("/new/{gamePatternId}")
    public QuestionDto newQuestionByGamePattern(@PathVariable Long gamePatternId,
                                                @RequestParam String title,
                                                @RequestParam String context,
                                                @RequestParam Integer weight,
                                                @RequestParam(value = "fileImage", required = false) MultipartFile multipartFile
    ) throws IOException {
        return questionService.createNewQuestion(gamePatternId,
                title,
                context,
                weight,
                null);
    }

    @GetMapping("/list/{gamePatternId}")
    public PageDto<QuestionDto> questionsListByGamePattern(@PathVariable Long gamePatternId,
                                                           @RequestParam(defaultValue = "0", required = false) int page,
                                                           @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return questionService.getQuestionsByGamePatternId(gamePatternId, page, pageSize);
    }

    @DeleteMapping("/{questionId}")
    public boolean deleteQuestion(@PathVariable Long questionId) {
        return questionService.deleteById(questionId);
    }

    @GetMapping("/relativeQuestions/{questionId}")
    public PageDto<QuestionDto> relativeQuestionsList(@PathVariable Long questionId,
                                                      @RequestParam(defaultValue = "0", required = false) int page,
                                                      @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return questionService.getRelativeQuestions(questionId, page, pageSize);
    }

    @PostMapping("/addRelativeQuestion/{questionId}/{relativeId}")
    public PageDto<QuestionDto> addRelativeQuestion(@PathVariable Long questionId,
                                                 @PathVariable Long relativeId,
                                                 @RequestParam(defaultValue = "0", required = false) int page,
                                                 @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return questionService.addRelativeQuestion(questionId, relativeId, page, pageSize);
    }
}
