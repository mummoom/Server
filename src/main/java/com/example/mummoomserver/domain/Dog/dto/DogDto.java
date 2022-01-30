package com.example.mummoomserver.domain.Dog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DogDto {
    private String dogName;
    private String dogBirth;
    private String dogType;
    private String dogSex;
    private String surgery;
}
