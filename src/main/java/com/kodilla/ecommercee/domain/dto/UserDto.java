package com.kodilla.ecommercee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class UserDto {
    int userId ;
    String name;
    boolean isActive;
    LocalDateTime expiryDate;
    int key;

    public UserDto(String name) {
        this.name = name;
    }
}
