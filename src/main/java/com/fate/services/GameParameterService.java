package com.fate.services;

import com.fate.dto.GameParameterDto;

import java.util.List;

public interface GameParameterService {

    List<GameParameterDto> getByGameId(Long id);
}
