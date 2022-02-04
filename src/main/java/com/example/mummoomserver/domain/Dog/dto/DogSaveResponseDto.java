package com.example.mummoomserver.domain.Dog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DogSaveResponseDto {
    private final Long dogIdx;
}
