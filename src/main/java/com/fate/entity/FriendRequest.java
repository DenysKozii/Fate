package com.fate.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friend_request")
public class FriendRequest extends BaseEntity{

    @NonNull
    private String invitorUsername;

    @NonNull
    private String acceptorUsername;

    @NonNull
    private Boolean status;

}