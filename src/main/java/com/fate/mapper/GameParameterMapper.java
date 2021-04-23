package com.fate.mapper;

import com.fate.dto.GameParameterDto;
import com.fate.entity.GameParameter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GameParameterMapper {
    GameParameterMapper INSTANCE = Mappers.getMapper(GameParameterMapper.class);

    GameParameterDto mapToDto(GameParameter gameParameter);
}
