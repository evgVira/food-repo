package com.example.testfoodapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyCaloriesReportResponseDto {

    private String name;

    private String dailyCaloriesNorm;

    private Boolean reachedNorm;

    private int consumedCalories;

    private LocalDate dateCaloriesTracking;

}
