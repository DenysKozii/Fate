package com.fate.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "questions")
public class Question extends BaseEntity{
    @NonNull
    private String title;
    @NonNull
    private String context;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_conditions",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "condition_id", referencedColumnName = "id"))
    private List<Question> questionConditions = new ArrayList<>();

    @Transient
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="game_pattern")
    private GamePattern gamePattern;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_relative_answer",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "relative_answer_id"))
    private List<Answer> relative_answers = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    private List<QuestionParameter> questionParameters = new ArrayList<>();

    private Integer weight;

}
