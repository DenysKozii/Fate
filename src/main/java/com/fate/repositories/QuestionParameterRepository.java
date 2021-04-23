package com.fate.repositories;

import com.fate.entity.Parameter;
import com.fate.entity.Question;
import com.fate.entity.QuestionParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuestionParameterRepository extends JpaRepository<QuestionParameter, Long>, PagingAndSortingRepository<QuestionParameter, Long> {
    List<QuestionParameter> findAllByQuestion(Question question);

    Page<QuestionParameter> findAllByQuestion(Question question, Pageable pageable);
}
