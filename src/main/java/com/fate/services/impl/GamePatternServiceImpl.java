package com.fate.services.impl;

import com.fate.dto.GamePatternDto;
import com.fate.entity.GamePattern;
import com.fate.entity.User;
import com.fate.exception.EntityNotFoundException;
import com.fate.mapper.GamePatternMapper;
import com.fate.pagination.PageDto;
import com.fate.pagination.PagesUtility;
import com.fate.repositories.GamePatternRepository;
import com.fate.repositories.QuestionRepository;
import com.fate.repositories.UserRepository;
import com.fate.services.GamePatternService;
import com.fate.services.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class GamePatternServiceImpl implements GamePatternService {
    private final GamePatternRepository gamePatternRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    public GamePatternDto createGamePattern(String title, Integer usersAmount, String username) {
        if (gamePatternRepository.findByTitle(title).isPresent())
            return GamePatternMapper.INSTANCE.mapToDto(gamePatternRepository.findByTitle(title).get());

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " doesn't exists!"));

        GamePattern gamePattern = new GamePattern();
        gamePattern.setTitle(title);
        gamePattern.setUsersAmount(usersAmount);
        gamePattern.getUsers().add(user);
        gamePatternRepository.save(gamePattern);

        return GamePatternMapper.INSTANCE.mapToDto(gamePattern);
    }

    @Override
    public PageDto<GamePatternDto> getGamePatternsByUser(String username, int page, int pageSize) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " doesn't exists!"));
        Page<GamePattern> result = gamePatternRepository.findByUsers(user, PagesUtility.createPageableUnsorted(page, pageSize));
        return PageDto.of(result.getTotalElements(), page, mapGamePattern(result.getContent()));
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
        userRepository.findAllByGamePatterns(gamePattern)
                .forEach(o -> o.getGamePatterns().remove(gamePattern));
        gamePattern.setUsers(new HashSet<>());
        gamePatternRepository.delete(gamePattern);
        return true;
    }
}