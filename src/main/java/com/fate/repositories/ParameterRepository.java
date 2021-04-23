package com.fate.repositories;

import com.fate.entity.AnswerParameter;
import com.fate.entity.GamePattern;
import com.fate.entity.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ParameterRepository extends JpaRepository<Parameter, Long>, PagingAndSortingRepository<Parameter, Long> {

    List<Parameter> findAllByGamePattern(GamePattern gamePattern);

    Page<Parameter> findAllByGamePattern(GamePattern gamePattern, Pageable pageable);

}
