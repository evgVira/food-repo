package com.example.testfoodapp.dto.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateFoodResponseDto {

    private UUID id;

    private String name;

    private Integer calories;

    private Integer proteins;

    private Integer fats;

    private Integer carbohydrates;

    private UUID userId;

    private LocalDate createdAt;

    private LocalDate updatedAt;

}
