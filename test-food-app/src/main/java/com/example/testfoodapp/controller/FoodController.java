package com.example.testfoodapp.controller;

import com.example.testfoodapp.dto.food.CreateFoodRequestDto;
import com.example.testfoodapp.dto.food.CreateFoodResponseDto;
import com.example.testfoodapp.dto.food.CreateFoodResponseDtoList;
import com.example.testfoodapp.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Контроллер блюда")
@RestController
@RequestMapping("/api/v1/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/user/{userId}/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового блюда", description = "создание нового блюда для пользователя")
    public CreateFoodResponseDto createFood(@Parameter(description = "тело запроса для создания блюда")
                                            @Valid @RequestBody CreateFoodRequestDto createFoodRequestDto,
                                            @Parameter(description = "id пользователя")
                                            @PathVariable("userId") UUID userId) {
        return foodService.createFood(createFoodRequestDto, userId);
    }

    @PostMapping("/user/{userId}/list/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового блюда", description = "создание нового блюда для пользователя")
    public CreateFoodResponseDtoList createFood(@Parameter(description = "тело запроса для создания блюда")
                                                  @RequestBody List<CreateFoodRequestDto> createFoodRequestDto,
                                                @Parameter(description = "id пользователя")
                                                  @PathVariable("userId") UUID userId) {
        return foodService.createFoods(createFoodRequestDto, userId);
    }

}
