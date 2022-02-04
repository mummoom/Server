package com.example.mummoomserver.domain.Dog.dto;

import com.example.mummoomserver.domain.Dog.entity.Dog;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DogSaveRequestDto {
    private Long userIdx;       //이걸 없애야 하나
    private String dogName;
    private String dogBirth;
    private String dogType;
    private String dogSex;
    private String surgery;
}