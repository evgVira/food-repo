package com.example.testfoodapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserResponseDto {

    private UUID id;

    private String name;

    private String email;

    private Integer age;

    private Integer weight;

    private Integer height;

    private String goal;

    private String dailyCalories;

}
