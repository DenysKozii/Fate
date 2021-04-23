package com.fate.entity;

import com.fate.enums.GameRequestStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "game_request")
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest extends BaseEntity{

    private String invitorUsername;

    private String  acceptorUsername;

    private Long gamePatternId;

    private GameRequestStatus status;


}
