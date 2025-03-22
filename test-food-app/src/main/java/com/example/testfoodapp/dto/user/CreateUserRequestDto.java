package com.example.testfoodapp.dto.user;

import com.example.testfoodapp.enums.UserGoal;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequestDto {

    @NotNull(message = "Имя пользователя не указано")
    @NotBlank
    private String name;

    @NotNull(message = "Email не указан")
    @NotBlank
    @Email
    private String email;

    @NotNull(message = "Возраст пользователя не указан")
    @Min(value = 6, message = "Возраст пользователя указан не корректно, введите значение от 6")
    @Max(value = 100, message = "Возраст пользователя указан не корректно, введите значение до 100")
    private Integer age;

    @NotNull(message = "Вес пользователя не указан")
    @Min(value = 30, message = "Вес указан не корректно, введите значение от 30")
    @Max(value = 200, message = "Вес указан не корректно, введите значение до 200")
    private Integer weight;

    @NotNull(message = "Рост пользователя не указан")
    @Min(value = 100, message = "Рост указан не корректно, введите значение от 100")
    @Max(value = 300, message = "Рост указан не корректно, введите значение до 300")
    private Integer height;

    @NotNull(message = "Цель расчета калорий не указана")
    private UserGoal goal;

}
