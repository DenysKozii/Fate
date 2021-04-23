package com.fate.services.impl;

import com.fate.dto.AnswerParameterDto;
import com.fate.dto.QuestionParameterDto;
import com.fate.entity.AnswerParameter;
import com.fate.entity.Question;
import com.fate.entity.QuestionParameter;
import com.fate.exception.EntityNotFoundException;
import com.fate.mapper.AnswerParameterMapper;
import com.fate.mapper.QuestionParameterMapper;
import com.fate.pagination.PageDto;
import com.fate.pagination.PagesUtility;
import com.fate.repositories.QuestionParameterRepository;
import com.fate.repositories.QuestionRepository;
import com.fate.services.QuestionParameterService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
public class QuestionParameterServiceImpl implements QuestionParameterService {
    private final QuestionParameterRepository questionParameterRepository;
    private final QuestionRepository questionRepository;

    @Override
    public PageDto<QuestionParameterDto> getParametersByQuestionId(Long questionId, int page, int pageSize) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question with id " + questionId + " not found"));

        Page<QuestionParameter> result = questionParameterRepository.findAllByQuestion(question, PagesUtility.createPageableUnsorted(page, pageSize));
        return PageDto.of(result.getTotalElements(), page, mapToDto(result.getContent()));
    }

    private List<QuestionParameterDto> mapToDto(List<QuestionParameter> questions) {
        return questions.stream()
                .map(QuestionParameterMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(Long parameterId, Integer appear, Integer disappear) {
        QuestionParameter questionParameter = questionParameterRepository.findById(parameterId)
                .orElseThrow(() -> new EntityNotFoundException("QuestionParameter with id " + parameterId + " not found"));
        questionParameter.setValueAppear(appear);
        questionParameter.setValueDisappear(disappear);
        questionParameterRepository.save(questionParameter);
        return true;
    }
}
