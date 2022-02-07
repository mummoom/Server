package com.example.mummoomserver.domain.Ingredients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Category {

    VEGETABLE("채소",0),
    FISH("생선",1),
    MEAT("육류",2),
    DAIRY("유제품",3),
    SHELLFISH("어패류",4),
    CEREAL("곡류",5),
    FRUIT("과일류",6),
    OTHER("기타",7);


    private final String name;
    private final int num;

    public static String getCategoryNameByNum(int num){
        return Arrays.stream(values())
                .filter(category -> category.num == num)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .getName();

    }




}
