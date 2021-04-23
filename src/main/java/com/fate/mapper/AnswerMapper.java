package com.fate.mapper;

import com.fate.dto.AnswerDto;
import com.fate.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

//    @BeanMapping( ignoreUnmappedSourceProperties={"relativeQuestions", "question"} )
    AnswerDto mapToDto(Answer answer);
}
