package com.example.mummoomserver.domain.Ingredients.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class SearchReqDto {

    @ApiModelProperty(example = "재료 이름")
    private String ingredientName;


    public SearchReqDto(){

    }

    public SearchReqDto(String ingredientName){
        this.ingredientName = ingredientName;
    }
}
