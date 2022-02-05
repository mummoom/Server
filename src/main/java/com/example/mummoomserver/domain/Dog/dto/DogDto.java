package com.example.mummoomserver.domain.Dog.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "강아지 정보", description = "이름, 생년월일, 종, 성별, 중성화 여부")
public class DogDto {
    private String dogName;
    private String dogBirth;
    private String dogType;
    private String dogSex;
    private String surgery;
}
