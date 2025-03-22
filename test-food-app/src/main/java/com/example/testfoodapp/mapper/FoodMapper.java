package com.example.testfoodapp.mapper;

import com.example.testfoodapp.dto.food.CreateFoodRequestDto;
import com.example.testfoodapp.dto.food.CreateFoodResponseDto;
import com.example.testfoodapp.model.Food;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FoodMapper {

    @Mapping(target = "name", source = "createFoodRequestDto.name")
    @Mapping(target = "calories", source = "createFoodRequestDto.calories")
    @Mapping(target = "proteins", source = "createFoodRequestDto.proteins")
    @Mapping(target = "fats", source = "createFoodRequestDto.fats")
    @Mapping(target = "carbohydrates", source = "createFoodRequestDto.carbohydrates")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDate.now())")
    Food mapToFood(CreateFoodRequestDto createFoodRequestDto, UUID userId);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "calories", source = "calories")
    @Mapping(target = "proteins", source = "proteins")
    @Mapping(target = "fats", source = "fats")
    @Mapping(target = "carbohydrates", source = "carbohydrates")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    CreateFoodResponseDto mapToCreateFoodResponseDto(Food food);

}
