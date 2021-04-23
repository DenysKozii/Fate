package com.fate.services;


import com.fate.dto.ParameterDto;
import com.fate.pagination.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ParameterService {

    PageDto<ParameterDto> getParametersByGamePatternId(Long gamePatternId, int page, int pageSize);

    boolean delete(Long parameterId);

    boolean create(ParameterDto parameterDto);
}
