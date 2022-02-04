package com.example.mummoomserver.domain.Ingredients.dto;

import com.example.mummoomserver.domain.Ingredients.entity.Ingredients;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 *  메인페이지에서 검색 결과로 전달해줄 재료에 대한 정보 DTO
 *
 */

@Getter @Setter
@AllArgsConstructor
public class IngredientSearchResultDto {


    private String name;
    private String category;
    private String imgUrl;
    private String warning;
    private String spec;
    private int score;

    //성분관련 정보
    private int kcal;
    private float dan;
    private float tan;
    private float gi;
    private float mu;
    private float water;
    private String effect;


    public IngredientSearchResultDto(Ingredients ingredient){

        this.name = ingredient.getName();
        this.category = ingredient.getCategory();
        this.imgUrl = ingredient.getImg_url();
        this.warning = ingredient.getWarning();
        this.spec = ingredient.getSpec();
        this.score = ingredient.getScore();

        this.kcal = ingredient.getKcal();
        this.dan = ingredient.getComponent().getDan();
        this.gi = ingredient.getComponent().getGi();
        this.mu = ingredient.getComponent().getMu();
        this.water = ingredient.getComponent().getWater();
        this.effect = ingredient.getComponent().getEffect();

    }




}
