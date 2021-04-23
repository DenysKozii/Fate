package com.fate.mapper;

import com.fate.dto.ParameterDto;
import com.fate.entity.Parameter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FateParameterMapper {
    FateParameterMapper INSTANCE = Mappers.getMapper(FateParameterMapper.class);

    ParameterDto mapToDto(Parameter parameter);
}
