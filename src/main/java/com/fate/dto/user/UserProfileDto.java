package com.fate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private Long id;

    private String username;

    public UserProfileDto(Long id) {
        this.id = id;
    }
}
