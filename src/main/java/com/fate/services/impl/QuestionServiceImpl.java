package com.fate.services.impl;

import com.fate.dto.QuestionDto;
import com.fate.entity.*;
import com.fate.exception.EntityNotFoundException;
import com.fate.mapper.QuestionMapper;
import com.fate.pagination.PageDto;
import com.fate.pagination.PagesUtility;
import com.fate.repositories.*;
import com.fate.services.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final GamePatternRepository gamePatternRepository;
    private final AnswerRepository answerRepository;
    private final QuestionParameterRepository questionParameterRepository;


    @Override
    public List<QuestionDto> getQuestionsByGamePatternId(Long gamePatternId, Long questionId) {
        List<Question> questions = questionRepository.findAllByGamePatternId(gamePatternId);
        return questions.stream()
                .filter(o -> !o.getId().equals(questionId))
                .map(QuestionMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PageDto<QuestionDto> getQuestionsByGamePatternId(Long gamePatternId, int page, int pageSize) {
        Page<Question> result = questionRepository.findAllByGamePatternId(gamePatternId, PagesUtility.createPageableUnsorted(page, pageSize));
        return PageDto.of(result.getTotalElements(), page, mapToDto(result.getContent()));
    }

    private List<QuestionDto> mapToDto(List<Question> questions) {
        return questions.stream()
                .map(QuestionMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    private Question createNewQuestionParameters(Long gamePatternId) {
        GamePattern gamePattern = gamePatternRepository.findById(gamePatternId)
                .orElseThrow(() -> new EntityNotFoundException("GamePattern with id " + gamePatternId + " not found"));
        Question question = new Question();
        question.setGamePattern(gamePattern);
        for (Parameter parameter : gamePattern.getParameters()) {
            QuestionParameter questionParameter = new QuestionParameter();
            questionParameter.setTitle(parameter.getTitle());
            questionParameter.setValueAppear(parameter.getLowestValue());
            questionParameter.setValueDisappear(parameter.getHighestValue());
            questionParameter.setQuestion(question);
            questionParameterRepository.save(questionParameter);
            question.getQuestionParameters().add(questionParameter);
        }
        return question;
    }

    @Override
    public QuestionDto createNewQuestion(Long gamePatternId, String title, String context, Integer weight, MultipartFile multipartFile) throws IOException {
        Question question = createNewQuestionParameters(gamePatternId);
        question.setTitle(title);
        question.setContext(context);
        question.setWeight(weight);
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        if (!fileName.isEmpty()){
//            question.setImage(String.format("/uploads/%s", fileName));
//            String uploadDir = request.getServletContext().getRealPath("/uploads");
//            Files.createDirectories(Paths.get(uploadDir));
//            multipartFile.transferTo(new File(String.format("%s/%s",uploadDir, fileName)));
//        }
        questionRepository.save(question);
        return QuestionMapper.INSTANCE.mapToDto(question);
    }

    @Override
    public List<QuestionDto> addRelativeQuestion(Long questionId, Long relativeId, Long gamePatternId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question with id " + questionId + " not found"));
        Question relativeQuestion = questionRepository.findById(relativeId)
                .orElseThrow(() -> new EntityNotFoundException("Question with id " + relativeId + " not found"));
        GamePattern gamePattern = gamePatternRepository.findById(gamePatternId)
                .orElseThrow(() -> new EntityNotFoundException("GamePattern with id " + gamePatternId + " not found"));

        question.getQuestionConditions().add(relativeQuestion);
        questionRepository.save(question);

        Set<Question> relativeQuestions = new HashSet<>(question.getQuestionConditions());
        relativeQuestions.add(question);
        return gamePattern.getQuestions().stream()
                .filter(o -> !relativeQuestions.contains(o))
                .map(QuestionMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question with id " + questionId + " not found"));
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        answers.forEach(answerRepository::delete);
        questionRepository.delete(question);
        return true;
    }


}
