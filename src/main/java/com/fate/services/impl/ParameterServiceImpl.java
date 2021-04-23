package com.fate.services.impl;

import com.fate.dto.ParameterDto;
import com.fate.entity.AnswerParameter;
import com.fate.entity.GamePattern;
import com.fate.entity.Parameter;
import com.fate.exception.EntityNotFoundException;
import com.fate.mapper.FateParameterMapper;
import com.fate.pagination.PageDto;
import com.fate.pagination.PagesUtility;
import com.fate.repositories.GamePatternRepository;
import com.fate.repositories.ParameterRepository;
import com.fate.services.ParameterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class ParameterServiceImpl implements ParameterService {

    private final ParameterRepository parameterRepository;
    private final GamePatternRepository gamePatternRepository;

    @Override
    public PageDto<ParameterDto> getParametersByGamePatternId(Long gamePatternId, int page, int pageSize) {
        GamePattern gamePattern = gamePatternRepository.findById(gamePatternId)
                .orElseThrow(()->new EntityNotFoundException("GamePattern with id " + gamePatternId + " not found"));

        Page<Parameter> result = parameterRepository.findAllByGamePattern(gamePattern, PagesUtility.createPageableUnsorted(page, pageSize));
        return PageDto.of(result.getTotalElements(), page, mapToDto(result.getContent()));
    }

    private List<ParameterDto> mapToDto(List<Parameter> parameters) {
        return parameters.stream()
                .map(FateParameterMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean create(ParameterDto parameterDto) {
        GamePattern gamePattern = gamePatternRepository.findById(parameterDto.getGamePattern().getId())
                .orElseThrow(()->new EntityNotFoundException("GamePattern with id " + parameterDto.getGamePattern().getId() + " not found"));
        Parameter parameter = new Parameter();
        parameter.setTitle(parameterDto.getTitle());
        parameter.setDefaultValue(parameterDto.getDefaultValue());
        parameter.setLowestValue(parameterDto.getLowestValue());
        parameter.setHighestValue(parameterDto.getHighestValue());
        parameter.setGamePattern(gamePattern);
        parameterRepository.save(parameter);
        return true;
    }


    @Override
    public boolean delete(Long parameterId) {
        Parameter parameter = parameterRepository.findById(parameterId)
                .orElseThrow(()->new EntityNotFoundException("Parameter with id " + parameterId + " not found"));
        parameterRepository.delete(parameter);
        return true;
    }
}
