package com.fate.services;


import com.fate.dto.AnswerDto;
import com.fate.dto.GameDto;
import com.fate.pagination.PageDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AnswerService {

    List<AnswerDto> getAnswersByQuestionId(Long questionId);

    PageDto<AnswerDto> getAnswersByQuestionId(Long questionId, int page, int pageSize);

    boolean deleteById(Long answerId);

    AnswerDto createNewAnswer(Long questionId, String context);

    GameDto answerInfluence(Long answerId, GameDto game, GameDto gameSecond);
}
