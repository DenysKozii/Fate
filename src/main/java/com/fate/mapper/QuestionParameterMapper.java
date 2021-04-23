package com.fate.mapper;

import com.fate.dto.QuestionParameterDto;
import com.fate.entity.QuestionParameter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionParameterMapper {
    QuestionParameterMapper INSTANCE = Mappers.getMapper(QuestionParameterMapper.class);

    QuestionParameterDto mapToDto(QuestionParameter questionParameter);
}
