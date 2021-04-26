package com.fate.services;

import com.fate.dto.QuestionDto;
import com.fate.pagination.PageDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuestionService {

    PageDto<QuestionDto> getQuestionsByGamePatternId(Long gamePatternId, int page, int pageSize);

    PageDto<QuestionDto> addRelativeQuestion(Long questionId, Long relativeId, int page, int pageSize);

    boolean deleteById(Long questionId);

    QuestionDto createNewQuestion(Long gamePatternId, String title, String context, Integer weight, MultipartFile multipartFile) throws IOException;

    PageDto<QuestionDto> getRelativeQuestions(Long questionId, int page, int pageSize);
}
