package com.example.testfoodapp.service.impl;

import com.example.testfoodapp.dto.food.CreateFoodRequestDto;
import com.example.testfoodapp.dto.food.CreateFoodResponseDto;
import com.example.testfoodapp.dto.food.CreateFoodResponseDtoList;
import com.example.testfoodapp.exception.ResourceNotFoundException;
import com.example.testfoodapp.mapper.FoodMapper;
import com.example.testfoodapp.model.Food;
import com.example.testfoodapp.repository.FoodRepository;
import com.example.testfoodapp.service.FoodService;
import com.example.testfoodapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodService.class.getName());

    private static final String FOOD_NOT_FOUND = "Food with id: %s not found";

    private static final String FOOD_SAVED = "Food with id: %s has been saved";

    private static final String USER_NOT_FOUND = "User with id: %s not found";

    private final FoodRepository foodRepository;

    private final FoodMapper foodMapper;

    private final UserService userService;

    @Override
    @Transactional
    public CreateFoodResponseDto createFood(CreateFoodRequestDto createFoodRequestDto, UUID userId) {
        checkUserExist(userId);
        Food food = foodMapper.mapToFood(createFoodRequestDto, userId);
        foodRepository.save(food);
        LOGGER.info(String.format(FOOD_SAVED, food.getId()));
        return foodMapper.mapToCreateFoodResponseDto(food);
    }

    @Override
    @Transactional
    public CreateFoodResponseDtoList createFoods(List<CreateFoodRequestDto> createFoodRequestDtoList, UUID userId) {
        checkUserExist(userId);
        List<Food> foods = createFoodRequestDtoList.stream()
                .map(currentDto -> foodMapper.mapToFood(currentDto, userId))
                .toList();
        foodRepository.saveAll(foods);
        foods.stream()
                .map(Food::getId)
                .peek(currentFoodId -> LOGGER.info(String.format(FOOD_SAVED, currentFoodId)))
                .close();
        return new CreateFoodResponseDtoList(foods.stream()
                .map(foodMapper::mapToCreateFoodResponseDto)
                .toList());

    }

    private Food findFoodById(UUID foodId) {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(FOOD_NOT_FOUND, foodId)));
    }

    private void checkUserExist(UUID userId) {
        if (!userService.checkUserExistById(userId)) {
            throw new ResourceNotFoundException(String.format(USER_NOT_FOUND, userId));
        }
    }

}
