package com.example.testfoodapp.dto.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateFoodResponseDtoList {

    private List<CreateFoodResponseDto> data;

}
