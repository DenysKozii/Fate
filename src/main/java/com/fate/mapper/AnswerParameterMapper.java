package com.fate.mapper;

import com.fate.dto.AnswerParameterDto;
import com.fate.entity.AnswerParameter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnswerParameterMapper {
    AnswerParameterMapper INSTANCE = Mappers.getMapper(AnswerParameterMapper.class);

    AnswerParameterDto mapToDto(AnswerParameter answerParameter);
}
