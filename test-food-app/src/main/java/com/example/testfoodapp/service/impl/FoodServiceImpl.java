package com.example.testfoodapp.service.impl;

import com.example.testfoodapp.dto.food.CreateFoodRequestDto;
import com.example.testfoodapp.dto.food.CreateFoodResponseDto;
import com.example.testfoodapp.dto.food.CreateFoodResponseDtoList;
import com.example.testfoodapp.exception.ResourceNotFoundException;
import com.example.testfoodapp.mapper.FoodMapper;
import com.example.testfoodapp.model.Food;
import com.example.testfoodapp.repository.FoodRepository;
import com.example.testfoodapp.repository.UserEntityRepository;
import com.example.testfoodapp.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodService.class.getName());

    private static final String FOOD_SAVED = "Food with id: %s has been saved";

    private static final String USER_NOT_FOUND = "User with id: %s not found";

    private static final String DATA_NOT_FOUND = "Data with this date: %s not found";

    private final FoodRepository foodRepository;

    private final FoodMapper foodMapper;

    private final UserEntityRepository userEntityRepository;

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
        return new CreateFoodResponseDtoList(foods.stream()
                .map(foodMapper::mapToCreateFoodResponseDto)
                .toList());
    }

    @Override
    public int getCaloriesStatisticByUser(UUID userId, LocalDate date) {
        checkUserExist(userId);
        List<Food> userDailyFoods = foodRepository.getAllByCreatedAtAndUserId(date, userId);
        if (userDailyFoods.isEmpty()) {
            throw new ResourceNotFoundException(String.format(DATA_NOT_FOUND, date));
        }
        return userDailyFoods.stream()
                .mapToInt(Food::getCalories)
                .sum();
    }

    @Override
    public Map<LocalDate, Integer> getCaloriesStatisticByUser(UUID userId, LocalDate dateFrom, LocalDate dateTo) {
        checkUserExist(userId);

        List<LocalDate> allFoodDays = Stream.iterate(dateFrom, date -> date.plusDays(1))
                .limit(DAYS.between(dateFrom, dateTo) + 1)
                .toList();

        Map<LocalDate, Integer> caloriesByDays = new HashMap<>();
        allFoodDays.forEach(currentDate -> {
            List<Food> allDailyFoods = foodRepository.getAllByCreatedAtAndUserId(currentDate, userId);
            int allDailyCalories = allDailyFoods.stream()
                    .mapToInt(Food::getCalories)
                    .sum();
            caloriesByDays.put(currentDate, allDailyCalories);
        });
        return caloriesByDays;
    }

    private void checkUserExist(UUID userId) {
        if (!userEntityRepository.existsById(userId)) {
            throw new ResourceNotFoundException(String.format(USER_NOT_FOUND, userId));
        }
    }
}
