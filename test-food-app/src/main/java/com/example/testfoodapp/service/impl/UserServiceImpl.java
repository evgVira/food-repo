package com.example.testfoodapp.service.impl;

import com.example.testfoodapp.dto.user.CreateUserRequestDto;
import com.example.testfoodapp.dto.user.CreateUserResponseDto;
import com.example.testfoodapp.dto.user.DailyCaloriesReportResponseDto;
import com.example.testfoodapp.dto.user.UserInfoResponseDto;
import com.example.testfoodapp.enums.PhysicalActivity;
import com.example.testfoodapp.enums.UserGender;
import com.example.testfoodapp.exception.ResourceNotFoundException;
import com.example.testfoodapp.exception.UserRequestException;
import com.example.testfoodapp.mapper.UserMapper;
import com.example.testfoodapp.model.UserEntity;
import com.example.testfoodapp.repository.UserEntityRepository;
import com.example.testfoodapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    private static final String USER_NOT_FOUND = "User with %s: %s not found";

    private static final String USER_FOUND_PARAM_ID = "id";

    private static final String USER_ALREADY_EXISTS = "User with email: %s already exist";

    private static final String USER_SAVED = "User with email: %s has been saved";

    private static final String USER_DELETED = "User with id: %s has been deleted";

    private final UserEntityRepository userEntityRepository;

    private final UserMapper userMapper;

    private final DailyCaloriesService dailyCaloriesService;

    @Override
    @Transactional
    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto, UserGender userGender, PhysicalActivity physicalActivity) {
        checkUserExistsByEmail(createUserRequestDto.getEmail());
        String dailyCalories = dailyCaloriesService.calculateDailyCalorieForUser(createUserRequestDto, userGender, physicalActivity);
        UserEntity userEntity = userMapper.mapToUserEntity(createUserRequestDto, dailyCalories);
        userEntityRepository.save(userEntity);
        LOGGER.info(String.format(USER_SAVED, createUserRequestDto.getEmail()));
        return userMapper.mapToCreateUerResponseDto(userEntity);
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        UserEntity userEntity = findUserById(userId);
        userEntityRepository.delete(userEntity);
        LOGGER.info(String.format(USER_DELETED, userId));
    }

    @Override
    public UserInfoResponseDto getUserInfoById(UUID userId) {
        UserEntity userEntity = findUserById(userId);
        return userMapper.mapToUserInfoResponseDto(userEntity);
    }

    @Override
    public boolean checkUserExistById(UUID userId) {
        return userEntityRepository.existsById(userId);
    }

    @Override
    public DailyCaloriesReportResponseDto getDailyCaloriesReport(UUID userId, LocalDate date) {
        int allDailyCalories = dailyCaloriesService.getCaloriesStatisticByUser(userId, date);
        String userDailyCaloriesNorm = userEntityRepository.getUserDailyCaloriesNorm(userId);
        boolean reachedNorm = dailyCaloriesService.checkUserReachedNorm(userDailyCaloriesNorm, allDailyCalories);
        String userName = userEntityRepository.getUserNameByUserId(userId);
        return userMapper.mapToDailyCaloriesReportResponse(userName, userDailyCaloriesNorm,
                reachedNorm, allDailyCalories, date);
    }

    @Override
    public List<DailyCaloriesReportResponseDto> getDailyCaloriesReportForDays(UUID userId, LocalDate dateFrom, LocalDate dateTo) {
        Map<LocalDate, Integer> allCaloriesByDays = dailyCaloriesService.getCaloriesStatisticByUser(userId, dateFrom, dateTo);
        String userDailyCaloriesNorm = userEntityRepository.getUserDailyCaloriesNorm(userId);
        String username = userEntityRepository.getUserNameByUserId(userId);
        return allCaloriesByDays.entrySet().stream()
                .map(entry -> {
                    boolean reachedNorm = dailyCaloriesService.checkUserReachedNorm(userDailyCaloriesNorm, entry.getValue());
                    return userMapper.mapToDailyCaloriesReportResponse(username, userDailyCaloriesNorm,
                            reachedNorm, entry.getValue(), entry.getKey());
                }).toList();
    }

    private UserEntity findUserById(UUID userId) {
        return userEntityRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, USER_FOUND_PARAM_ID, userId)));
    }

    private void checkUserExistsByEmail(String email) {
        if (userEntityRepository.existsByEmail(email)) {
            throw new UserRequestException(String.format(USER_ALREADY_EXISTS, email));
        }
    }

}
