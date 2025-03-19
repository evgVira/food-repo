package com.example.testfoodapp.dto.user;

import com.example.testfoodapp.enums.UserGoal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequestDto {

    @NotNull(message = "Имя пользователя не указано")
    private String name;

    @NotNull(message = "Email не указан")
    @Email(message = "Email указан не корректно")
    private String email;

    @NotNull(message = "Возраст пользователя не указан")
    private Integer age;

    @NotNull(message = "Вес пользователя не указан")
    private Integer weight;

    @NotNull(message = "Рост пользователя не указан")
    private Integer height;

    @NotNull(message = "Цель расчета калорий не указана")
    private UserGoal goal;

}
