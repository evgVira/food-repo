package com.example.testfoodapp.service;

import com.example.testfoodapp.dto.user.CreateUserRequestDto;
import com.example.testfoodapp.dto.user.CreateUserResponseDto;
import com.example.testfoodapp.dto.user.DailyCaloriesReportResponseDto;
import com.example.testfoodapp.enums.PhysicalActivity;
import com.example.testfoodapp.enums.UserGender;
import com.example.testfoodapp.enums.UserGoal;
import com.example.testfoodapp.exception.ResourceNotFoundException;
import com.example.testfoodapp.exception.UserRequestException;
import com.example.testfoodapp.mapper.UserMapper;
import com.example.testfoodapp.model.UserEntity;
import com.example.testfoodapp.repository.FoodRepository;
import com.example.testfoodapp.repository.UserEntityRepository;
import com.example.testfoodapp.service.impl.DailyCaloriesService;
import com.example.testfoodapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final UUID USER_ID = UUID.randomUUID();

    private static final String USER_NAME = "name";

    private static final String USER_EMAIL = "email@gmail.com";

    private static final String DAILY_CALORIES = "daily calories";

    private static final String DAILY_CALORIES_NORM = "daily calories norm";

    private static final LocalDate DATE_CALORIES_TRACKING = LocalDate.now();

    private static final int CONSUMED_CALORIES = 100;

    private static final boolean REACHED_NORM = false;

    private static final int USER_AGE = 30;

    private static final int USER_WEIGHT = 70;

    private static final int USER_HEIGHT = 170;

    private static final String USER_ALREADY_EXISTS = "User with email: %s already exist";

    private static final String DATA_NOT_FOUND = "Data with this date: %s not found";

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private DailyCaloriesService dailyCaloriesService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private CreateUserRequestDto createUserRequestDto;
    private CreateUserResponseDto createUserResponseDto;
    private DailyCaloriesReportResponseDto dailyCaloriesReportResponseDto;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        createUserRequestDto = new CreateUserRequestDto(
                USER_NAME,
                USER_EMAIL,
                USER_AGE,
                USER_WEIGHT,
                USER_HEIGHT,
                UserGoal.WEIGHT_LOSS
        );
        createUserResponseDto = new CreateUserResponseDto(
                USER_ID,
                USER_NAME,
                USER_EMAIL,
                USER_AGE,
                USER_WEIGHT,
                USER_HEIGHT,
                UserGoal.WEIGHT_LOSS.name(),
                DAILY_CALORIES
        );
        userEntity = new UserEntity(
                USER_ID,
                USER_NAME,
                USER_EMAIL,
                USER_AGE,
                USER_WEIGHT,
                USER_HEIGHT,
                UserGoal.WEIGHT_LOSS,
                DAILY_CALORIES
        );
        dailyCaloriesReportResponseDto = new DailyCaloriesReportResponseDto(
                USER_NAME,
                DAILY_CALORIES_NORM,
                REACHED_NORM,
                CONSUMED_CALORIES,
                DATE_CALORIES_TRACKING
        );
    }

    @Test
    void createUserSuccess() {
        when(userEntityRepository.existsByEmail(USER_EMAIL))
                .thenReturn(false);
        when(dailyCaloriesService.calculateDailyCalorieForUser(createUserRequestDto, UserGender.MALE, PhysicalActivity.NORMAL))
                .thenReturn(DAILY_CALORIES);
        when(userMapper.mapToUserEntity(createUserRequestDto, DAILY_CALORIES))
                .thenReturn(userEntity);
        when(userMapper.mapToCreateUerResponseDto(userEntity))
                .thenReturn(createUserResponseDto);

        CreateUserResponseDto result = userService.createUser(createUserRequestDto, UserGender.MALE, PhysicalActivity.NORMAL);

        verify(userEntityRepository, times(1)).save(userEntity);

        assertThat(result)
                .isNotNull()
                .isEqualTo(createUserResponseDto);
    }

    @Test
    void createUserFailed() {
        when(userEntityRepository.existsByEmail(USER_EMAIL))
                .thenReturn(true);

        UserRequestException exception = assertThrows(UserRequestException.class, () -> userService.createUser(createUserRequestDto, UserGender.MALE, PhysicalActivity.NORMAL));

        verify(userEntityRepository, times(1)).existsByEmail(USER_EMAIL);
        assertEquals(exception.getMessage(), String.format(USER_ALREADY_EXISTS, USER_EMAIL));
    }

    @Test
    void getDailyCaloriesReportSuccess() {
        when(dailyCaloriesService.getCaloriesStatisticByUser(USER_ID, DATE_CALORIES_TRACKING))
                .thenReturn(CONSUMED_CALORIES);
        when(userEntityRepository.getUserDailyCaloriesNorm(USER_ID))
                .thenReturn(DAILY_CALORIES_NORM);
        when(dailyCaloriesService.checkUserReachedNorm(DAILY_CALORIES_NORM, CONSUMED_CALORIES))
                .thenReturn(true);
        when(userEntityRepository.getUserNameByUserId(USER_ID))
                .thenReturn(USER_NAME);
        when(userMapper.mapToDailyCaloriesReportResponse(USER_NAME, DAILY_CALORIES_NORM, true, CONSUMED_CALORIES, DATE_CALORIES_TRACKING))
                .thenReturn(dailyCaloriesReportResponseDto);

        DailyCaloriesReportResponseDto result = userService.getDailyCaloriesReport(USER_ID, DATE_CALORIES_TRACKING);

        assertThat(result)
                .isNotNull()
                .isEqualTo(dailyCaloriesReportResponseDto);
    }

    @Test
    void getDailyCaloriesReportFailed() {
        when(dailyCaloriesService.getCaloriesStatisticByUser(USER_ID, DATE_CALORIES_TRACKING))
                .thenThrow(new ResourceNotFoundException(String.format(DATA_NOT_FOUND, DATE_CALORIES_TRACKING)));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> userService.getDailyCaloriesReport(USER_ID, DATE_CALORIES_TRACKING));

        assertEquals(exception.getMessage(), String.format(DATA_NOT_FOUND, DATE_CALORIES_TRACKING));
    }

}
