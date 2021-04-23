package com.fate.services;

import com.fate.dto.QuestionDto;
import com.fate.pagination.PageDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuestionService {
    List<QuestionDto> getQuestionsByGamePatternId(Long gamePatternId, Long questionId);

    PageDto<QuestionDto> getQuestionsByGamePatternId(Long gamePatternId, int page, int pageSize);

    List<QuestionDto> addRelativeQuestion(Long questionId, Long relativeId, Long gamePatternId);

    boolean deleteById(Long questionId);

    QuestionDto createNewQuestion(Long gamePatternId, String title, String context, Integer weight, MultipartFile multipartFile) throws IOException;
}
