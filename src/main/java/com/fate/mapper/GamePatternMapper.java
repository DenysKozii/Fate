package com.fate.mapper;

import com.fate.dto.GamePatternDto;
import com.fate.entity.GamePattern;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GamePatternMapper {
    GamePatternMapper INSTANCE = Mappers.getMapper(GamePatternMapper.class);

    GamePatternDto mapToDto(GamePattern gamePattern);
}
