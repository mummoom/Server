package com.example.mummoomserver.domain.Dog.dto;

import com.example.mummoomserver.domain.Dog.entity.Dog;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "강아지 정보", description = "강아지 index, 이름, 생년월일, 종, 성별, 중성화 여부")
public class DogListResponseDto {
    private Long dogIdx;
    private String dogName;
    private String dogBirth;
    private String dogType;
    private String dogSex;
    private String surgery;

    public DogListResponseDto(Dog entity){
        this.dogIdx = entity.getDogIdx();
        this.dogName = entity.getDogName();
        this.dogBirth = entity.getDogBirth();
        this.dogType = entity.getDogType();
        this.dogSex = entity.getDogSex();
        this.surgery = entity.getSurgery();
    }
}