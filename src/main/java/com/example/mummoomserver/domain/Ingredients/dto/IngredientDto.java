package com.example.mummoomserver.domain.Ingredients.dto;

import lombok.*;

@Getter @Setter
@RequiredArgsConstructor
public class IngredientDto {
    private String name;
    private String category;
    private String imgUrl;
    private String warning;
    private String spec;
    private int score;
    private int kcal;
}
