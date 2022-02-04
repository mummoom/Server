package com.example.mummoomserver.domain.Dog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class DogDto {
    private String dogName;
    private String dogBirth;
    private String dogType;
    private String dogSex;
    private String surgery;
}
