package com.example.testfoodapp.service;

import com.example.testfoodapp.dto.food.CreateFoodRequestDto;
import com.example.testfoodapp.dto.food.CreateFoodResponseDto;
import com.example.testfoodapp.dto.food.CreateFoodResponseDtoList;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FoodService {

    CreateFoodResponseDto createFood(CreateFoodRequestDto createFoodRequestDto, UUID userId);

    CreateFoodResponseDtoList createFoods(List<CreateFoodRequestDto> createFoodRequestDtoList, UUID userId);

    int getCaloriesStatisticByUser(UUID userId, LocalDate date);

    Map<LocalDate, Integer> getCaloriesStatisticByUser(UUID userId, LocalDate dateFrom, LocalDate dateTo);
}
