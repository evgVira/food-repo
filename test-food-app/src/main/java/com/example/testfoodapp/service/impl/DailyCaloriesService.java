package com.example.testfoodapp.service.impl;

import com.example.testfoodapp.dto.user.CreateUserRequestDto;
import com.example.testfoodapp.enums.PhysicalActivity;
import com.example.testfoodapp.enums.UserGender;
import org.springframework.stereotype.Service;

@Service
public class DailyCaloriesService {

    private static final String WEIGHT_LOSS_DAILY_CALORIES = "Для похудения: %s-%s ккал";

    private static final String MAINTENANCE_DAILY_CALORIES = "Для поддержания веса: %s ккал";

    private static final String MUSCLE_GAIN_DAILY_CALORIES = "Для набора массы: %s-%s ккал";

    public String calculateDailyCalorieForUser(CreateUserRequestDto user, UserGender userGender, PhysicalActivity physicalActivity) {

        double bmr = switch (userGender) {
            case MALE -> 88.36 + (13.4 * user.getWeight()) + (4.8 * user.getHeight()) - (5.7 * user.getAge());
            case FEMALE -> 447.6 + (9.2 * user.getWeight()) + (3.1 * user.getHeight()) - (4.3 * user.getAge());
        };

        long tde = Math.round(bmr * physicalActivity.getValue());

        return switch (user.getGoal()) {
            case WEIGHT_LOSS -> String.format(WEIGHT_LOSS_DAILY_CALORIES, (tde - 300), (tde - 500));
            case MAINTENANCE -> String.format(MAINTENANCE_DAILY_CALORIES, tde);
            case MUSCLE_GAIN -> String.format(MUSCLE_GAIN_DAILY_CALORIES, (tde + 300), (tde + 500));
        };
    }
}
