package com.example.testfoodapp.controller;

import com.example.testfoodapp.dto.user.CreateUserRequestDto;
import com.example.testfoodapp.dto.user.CreateUserResponseDto;
import com.example.testfoodapp.dto.user.DailyCaloriesReportResponseDto;
import com.example.testfoodapp.dto.user.UserInfoResponseDto;
import com.example.testfoodapp.enums.PhysicalActivity;
import com.example.testfoodapp.enums.UserGender;
import com.example.testfoodapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "Контроллер пользователя")
public class UserController {

    private final UserService userService;

    @PostMapping("/create/gender/{gender}/tde/{tde}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "создание пользователя", description = "создание нового пользователя в базе данных")
    public CreateUserResponseDto createUser(@Parameter(description = "тело запроса для создания пользователя", required = true)
                                            @Valid @RequestBody CreateUserRequestDto createUserRequestDto,
                                            @Parameter(description = "пол пользователя для расчета калорий", required = true)
                                            @PathVariable("gender") UserGender userGender,
                                            @Parameter(description = "уровень физической активности", required = true)
                                            @PathVariable("tde") PhysicalActivity physicalActivity) {
        return userService.createUser(createUserRequestDto, userGender, physicalActivity);
    }

    @DeleteMapping("/{userId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "удаление пользователя", description = "удаление пользователя из базы данных")
    public void deleteUser(@Parameter(description = "id пользователя", required = true)
                           @PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}/info")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "получение информации о пользователе", description = "получение информации о пользователе по id")
    public UserInfoResponseDto findUserById(@Parameter(description = "id пользователя", required = true)
                                            @PathVariable("userId") UUID userId) {
        return userService.getUserInfoById(userId);
    }

    @GetMapping("/daily-calories/user/{userId}/date/{date}/info")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "получение информации о потребленных калориях", description = "получение информации о потребленных калориях за день")
    public DailyCaloriesReportResponseDto getCaloriesStatisticByUser(@Parameter(description = "id пользователя", required = true)
                                                                     @PathVariable("userId") UUID userId,
                                                                     @Parameter(description = "date", required = true)
                                                                     @PathVariable("date") LocalDate date) {
        return userService.getDailyCaloriesReport(userId, date);
    }

    @GetMapping("/daily-calories/user/{userId}/date/from/{dateFrom}/to/{dateTo}/info")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "получение информации о потребленных калориях", description = "полуение информации о потребленных калориях за несколько дней")
    public List<DailyCaloriesReportResponseDto> getCaloriesStatisticByUserForDays(@Parameter(description = "id пользоавтеля", required = true)
                                                                                  @PathVariable("userId") UUID userId,
                                                                                  @Parameter(description = "начальная дата формирования отчета", required = true)
                                                                                  @PathVariable("dateFrom") LocalDate dateFrom,
                                                                                  @Parameter(description = "конечная дата формирования отчета", required = true)
                                                                                  @PathVariable("dateTo") LocalDate dateTo) {
        return userService.getDailyCaloriesReportForDays(userId, dateFrom, dateTo);
    }

}
