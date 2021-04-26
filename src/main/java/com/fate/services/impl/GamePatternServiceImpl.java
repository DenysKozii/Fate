package com.fate.services.impl;

import com.fate.dto.GamePatternDto;
import com.fate.entity.*;
import com.fate.exception.EntityNotFoundException;
import com.fate.mapper.GamePatternMapper;
import com.fate.pagination.PageDto;
import com.fate.pagination.PagesUtility;
import com.fate.repositories.GamePatternRepository;
import com.fate.repositories.ParameterRepository;
import com.fate.repositories.QuestionRepository;
import com.fate.repositories.UserRepository;
import com.fate.services.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class GamePatternServiceImpl implements GamePatternService {
    private final GamePatternRepository gamePatternRepository;
    private final ParameterService parameterService;
    private final GameService gameService;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;


    @Override
    public GamePatternDto createGamePattern(Long orderId, String title, Integer usersAmount) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        if (gamePatternRepository.findByTitle(title).isPresent())
            return GamePatternMapper.INSTANCE.mapToDto(gamePatternRepository.findByTitle(title).get());

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " doesn't exists!"));

        GamePattern gamePattern = new GamePattern();
        if (orderId != null){
            gamePattern.setOrderId(orderId);
        } else {
            gamePattern.setOrderId((long) gamePatternRepository.findAll().size());
        }
        gamePattern.setTitle(title);
        gamePattern.setUsersAmount(usersAmount);
        gamePattern.getUsers().add(user);
        gamePatternRepository.save(gamePattern);

        return GamePatternMapper.INSTANCE.mapToDto(gamePattern);
    }

    @Override
    public PageDto<GamePatternDto> getGamePatterns(int page, int pageSize) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " doesn't exists!"));

        List<GamePattern> available = gamePatternRepository.findByUsers(user);
        Page<GamePattern> result = gamePatternRepository.findAll(PagesUtility.createPageableUnsorted(page, pageSize));

        List<GamePatternDto> gamePatternDtos = mapGamePattern(result.getContent());
        List<String> availableTitles = mapGamePattern(available).stream()
                .map(GamePatternDto::getTitle)
                .collect(Collectors.toList());;

        gamePatternDtos.stream()
                .filter(gp->availableTitles.contains(gp.getTitle()))
                .forEach(o->o.setAvailable(true));
        gamePatternDtos.sort(Comparator.comparingLong(GamePatternDto::getOrderId));
        return PageDto.of(result.getTotalElements(), page, gamePatternDtos);
    }

    private List<GamePatternDto> mapGamePattern(List<GamePattern> source) {
        return source.stream()
                .map(GamePatternMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Long gamePatternId) {
        GamePattern gamePattern = gamePatternRepository.findById(gamePatternId)
                .orElseThrow(() -> new EntityNotFoundException("GamePattern with id " + gamePatternId + " not found"));
        questionRepository.findAllByGamePatternId(gamePatternId).forEach(o -> questionService.deleteById(o.getId()));
        gamePattern.setUsers(null);
        gamePattern.getParameters().stream()
                .map(Parameter::getId)
                .forEach(parameterService::deleteById);
        gamePattern.setParameters(null);
        gamePattern.getGames().stream()
                .map(Game::getId)
                .forEach(gameService::deleteById);
        gamePattern.setGames(null);
        gamePatternRepository.save(gamePattern);
        gamePatternRepository.delete(gamePattern);
        return true;
    }

    @Override
    public boolean updateAvailable(Long gamePatternId) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " doesn't exists!"));
        GamePattern gamePattern = gamePatternRepository.findById(gamePatternId)
                .orElseThrow(() -> new EntityNotFoundException("GamePattern with id " + gamePatternId + " not found"));
        gamePattern.getUsers().add(user);
        gamePatternRepository.save(gamePattern);
        return true;
    }
}
