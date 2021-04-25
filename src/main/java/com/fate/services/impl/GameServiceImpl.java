package com.fate.services.impl;

import com.fate.dto.*;
import com.fate.entity.*;
import com.fate.enums.GameStatus;
import com.fate.exception.EntityNotFoundException;
import com.fate.mapper.GameMapper;
import com.fate.mapper.GameRequestMapper;
import com.fate.mapper.QuestionMapper;
import com.fate.pagination.PageDto;
import com.fate.pagination.PagesUtility;
import com.fate.repositories.*;
import com.fate.services.AnswerService;
import com.fate.services.AuthorizationService;
import com.fate.services.GameParameterService;
import com.fate.services.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GamePatternRepository gamePatternRepository;
    private final QuestionRepository questionRepository;
    private final ParameterRepository parameterRepository;
    private final GameParameterRepository gameParameterRepository;
    private final QuestionParameterRepository questionParameterRepository;
    private final UserRepository userRepository;
    private final AnswerService answerService;
    private final GameParameterService gameParameterService;
    private final AuthorizationService authorizationService;


    @Override
    public GameDto startNewGame(Long gamePatternId) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " doesn't exists!"));

        GamePattern gamePattern = gamePatternRepository.findById(gamePatternId)
                .orElseThrow(() -> new EntityNotFoundException("GamePattern with id " + gamePatternId + " not found"));

        Game game = new Game();
        game.setGameStatus(GameStatus.RUNNING);
        game.setGamePattern(gamePattern);
        createParameters(game, gamePattern);
        gameRepository.save(game);
        game.setQuestionsPull(changeQuestions(game));
        game.setUser(user);
        gameRepository.save(game);

        return mapToDto(game);
    }

    private GameDto mapToDto(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setGameStatus(game.getGameStatus());
        gameDto.setGamePatternId(game.getGamePattern().getId());

        QuestionDto question = nextQuestion(game);
        gameDto.setQuestion(question);

        if(question != null) {
            List<AnswerDto> answers = answerService.getAnswersByQuestionId(question.getId());
            gameDto.setAnswers(answers);
        }

        List<GameParameterDto> parameters = gameParameterService.getByGameId(game.getId());
        gameDto.setParameters(parameters);

        return gameDto;
    }

    private void createParameters(Game game, GamePattern gamePattern) {
        List<Parameter> parameters = parameterRepository.findAllByGamePattern(gamePattern);
        for (Parameter parameter : parameters) {
            GameParameter gameParameter = new GameParameter();
            gameParameter.setParameter(parameter);
            gameParameter.setTitle(parameter.getTitle());
            gameParameter.setValue(parameter.getDefaultValue());
            gameParameter.setGame(game);
            gameParameterRepository.save(gameParameter);
            game.getParameters().add(gameParameter);
        }
    }

    private List<Question> changeQuestions(Game game) {
        List<Question> questions = questionRepository.findAllByGamePatternId(game.getGamePattern().getId());
        return questions.stream()
                .filter(question -> questionParameterRepository
                        .findAllByQuestion(question).stream()
                        .noneMatch(parameter ->
                                parameter.getValueAppear() > gameParameterRepository
                                        .findAllByTitleAndGame(parameter.getTitle(), game)
                                        .orElseThrow(() -> new EntityNotFoundException("GameParameter with title " + parameter.getTitle() + " not found")).getValue()
                                        || parameter.getValueDisappear() < gameParameterRepository
                                        .findAllByTitleAndGame(parameter.getTitle(), game)
                                        .orElseThrow(() -> new EntityNotFoundException("GameParameter with title " + parameter.getTitle() + " not found")).getValue()))
                .filter(o -> checkQuestions(game, o))
                .collect(Collectors.toList());
    }

    private boolean checkQuestions(Game game, Question question) {
        return game.getQuestionsPull().containsAll(question.getQuestionConditions());
    }

    @Override
    public GameDto saveGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));
        game.setGameStatus(GameStatus.PAUSED);
        gameRepository.save(game);
        return GameMapper.INSTANCE.mapToDto(game);
    }

    @Override
    public PageDto<GameDto> savedGames(int page, int pageSize) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " doesn't exists!"));

        Page<Game> result = gameRepository.findByUser(user, PagesUtility.createPageableUnsorted(page, pageSize));
        return PageDto.of(result.getTotalElements(), page, mapToDto(result.getContent()));
    }

    private List<GameDto> mapToDto(List<Game> games) {
        return games.stream()
                .filter(o->GameStatus.PAUSED.equals(o.getGameStatus()))
                .map(GameMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GameDto loadGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));
        game.setGameStatus(GameStatus.RUNNING);
        gameRepository.save(game);
        return GameMapper.INSTANCE.mapToDto(game);
    }

    @Override
    public QuestionDto nextQuestion(Game game) {
        List<Question> questions = changeQuestions(game);
        game.setQuestionsPull(questions);
        gameRepository.save(game);
        if (questions.size() == 0)
            return null;
        Question question = randomQuestion(questions);
        return QuestionMapper.INSTANCE.mapToDto(question);
    }

    private Question randomQuestion(List<Question> questions) {
        long summary = questions.stream()
                .map(Question::getWeight)
                .reduce(0, Integer::sum);
        double random = Math.random() * summary;
        long counter = 0;
        for (Question question : questions) {
            counter += question.getWeight();
            if (counter >= random)
                return question;
        }
        return questions.get(0);
    }
}
