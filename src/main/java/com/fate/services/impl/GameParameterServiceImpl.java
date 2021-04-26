package com.fate.services.impl;

import com.fate.dto.GameParameterDto;
import com.fate.entity.Game;
import com.fate.entity.GameParameter;
import com.fate.exception.EntityNotFoundException;
import com.fate.mapper.GameParameterMapper;
import com.fate.repositories.GameParameterRepository;
import com.fate.repositories.GameRepository;
import com.fate.services.GameParameterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GameParameterServiceImpl implements GameParameterService {
    private final GameParameterRepository gameParameterRepository;
    private final GameRepository gameRepository;

    @Override
    public List<GameParameterDto> getByGameId(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Game with id " + id + " not found"));
        return gameParameterRepository.findAllByGame(game).stream()
                .map(GameParameterMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Long id) {
        GameParameter gameParameter = gameParameterRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("GameParameter with id " + id + " not found"));
        gameParameter.setParameter(null);
        gameParameterRepository.save(gameParameter);
        gameParameterRepository.delete(gameParameter);
        return true;
    }
}
