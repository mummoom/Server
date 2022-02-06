package com.example.mummoomserver.domain.Ingredients.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchReqDto {

    @ApiModelProperty(example = "재료 이름")
    private final String ingredientName;
}
