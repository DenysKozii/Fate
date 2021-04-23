package com.fate.controllers.parameters;

import com.fate.dto.QuestionParameterDto;
import com.fate.entity.User;
import com.fate.pagination.PageDto;
import com.fate.services.QuestionParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questionParameter")
public class QuestionParameterController {

    private final QuestionParameterService questionParameterService;

    @PostMapping("/update/{parameterId}")
    public boolean update(@PathVariable Long parameterId,
                          @RequestParam Integer appear,
                          @RequestParam Integer disappear) {
        return questionParameterService.update(parameterId, appear, disappear);
    }

    @GetMapping("/list/{questionId}")
    public PageDto<QuestionParameterDto> list(@PathVariable Long questionId,
                                              @RequestParam(defaultValue = "0", required = false) int page,
                                              @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return questionParameterService.getParametersByQuestionId(questionId, page, pageSize);
    }
}
