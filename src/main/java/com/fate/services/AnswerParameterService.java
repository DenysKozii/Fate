package com.fate.services;


import com.fate.dto.AnswerParameterDto;
import com.fate.pagination.PageDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AnswerParameterService {

    boolean update(Long parameterId, Integer value);

    PageDto<AnswerParameterDto> getParametersByAnswerId(Long id, int page, int pageSize);
}
