package com.fate.mapper;

import com.fate.dto.GameParameterDto;
import com.fate.dto.GameRequestDto;
import com.fate.entity.GameParameter;
import com.fate.entity.GameRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GameRequestMapper {
    GameRequestMapper INSTANCE = Mappers.getMapper(GameRequestMapper.class);

    GameRequestDto mapToDto(GameRequest gameRequest);
}
