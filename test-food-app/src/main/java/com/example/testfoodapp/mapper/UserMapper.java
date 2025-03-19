package com.example.testfoodapp.mapper;

import com.example.testfoodapp.dto.user.CreateUserRequestDto;
import com.example.testfoodapp.dto.user.CreateUserResponseDto;
import com.example.testfoodapp.dto.user.UserInfoResponseDto;
import com.example.testfoodapp.model.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "name", source = "createUserRequestDto.name")
    @Mapping(target = "email", source = "createUserRequestDto.email")
    @Mapping(target = "age", source = "createUserRequestDto.age")
    @Mapping(target = "weight", source = "createUserRequestDto.weight")
    @Mapping(target = "height", source = "createUserRequestDto.height")
    @Mapping(target = "goal", source = "createUserRequestDto.goal")
    @Mapping(target = "dailyCalories", source = "dailyCalories")
    UserEntity mapToUserEntity(CreateUserRequestDto createUserRequestDto, String dailyCalories);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "goal", source = "goal")
    @Mapping(target = "dailyCalories", source = "dailyCalories")
    CreateUserResponseDto mapToCreateUerResponseDto(UserEntity userEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "goal", source = "goal")
    @Mapping(target = "dailyCalories", source = "dailyCalories")
    UserInfoResponseDto mapToUserInfoResponseDto(UserEntity userEntity);

}
