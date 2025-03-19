package com.example.testfoodapp.dto.food;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateFoodRequestDto {

    @NotNull(message = "Название блюда не указано")
    private String name;

    @NotNull(message = "Кол-во калорий не указано")
    private Integer calories;

    @NotNull(message = "Кол-во белков не указано")
    private Integer proteins;

    @NotNull(message = "Кол-во жиров не указано")
    private Integer fats;

    @NotNull(message = "Кол-во углеводов не указано")
    private Integer carbohydrates;

}
