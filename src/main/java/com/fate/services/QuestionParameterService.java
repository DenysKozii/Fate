package com.fate.services;


import com.fate.dto.QuestionParameterDto;
import com.fate.pagination.PageDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface QuestionParameterService {

    PageDto<QuestionParameterDto> getParametersByQuestionId(Long questionId, int page, int pageSize);

    boolean update(Long parameterId, Integer appear, Integer disappear);
}
