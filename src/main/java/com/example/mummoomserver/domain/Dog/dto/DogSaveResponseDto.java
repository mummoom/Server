package com.example.mummoomserver.domain.Dog.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@ApiModel(value = "강아지 Index")
public class DogSaveResponseDto {
    private final Long dogIdx;
}
