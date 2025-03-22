package com.example.testfoodapp.service;

import com.example.testfoodapp.dto.user.CreateUserRequestDto;
import com.example.testfoodapp.dto.user.CreateUserResponseDto;
import com.example.testfoodapp.dto.user.DailyCaloriesReportResponseDto;
import com.example.testfoodapp.dto.user.UserInfoResponseDto;
import com.example.testfoodapp.enums.PhysicalActivity;
import com.example.testfoodapp.enums.UserGender;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserService {

    CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto, UserGender userGender, PhysicalActivity physicalActivity);

    void deleteUser(UUID userId);

    UserInfoResponseDto getUserInfoById(UUID userId);

    boolean checkUserExistById(UUID userId);

    DailyCaloriesReportResponseDto getDailyCaloriesReport(UUID userId, LocalDate date);

    List<DailyCaloriesReportResponseDto> getDailyCaloriesReportForDays(UUID userId, LocalDate dateFrom, LocalDate dateTo);
}
