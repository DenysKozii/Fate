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
@Table(name = "parameters")
public class Parameter extends BaseEntity {

    private String title;

    private Integer defaultValue;

    private Integer highestValue;

    private Integer lowestValue;

    private Boolean visible = false;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_game_pattern")
    private GamePattern gamePattern;

}
