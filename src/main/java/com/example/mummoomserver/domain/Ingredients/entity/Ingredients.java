package com.example.mummoomserver.domain.Ingredients.entity;

import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.domain.Component.entity.Component;
import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity

@Table(name="Ingredients")
@JsonPropertyOrder({"ingredientsIdx","name","category","imgUrl","kcal","warning","spec","score"})
public class Ingredients extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientsIdx;

    @ManyToOne
    @JoinColumn(name="componentIdx") // fk 이름 component_idx 로 설정됨
    @JsonManagedReference
    @ApiModelProperty(example = "성분")
    private Component component;

    @Column(nullable = false)
    @ApiModelProperty(example = "재료 이름")
    private String name;

    @Column(nullable = false)
    @ApiModelProperty(example = "카테고리")
    private String category;

    @Column(nullable = false)
    @ApiModelProperty(example = "이미지 URL")
    private String imgUrl;

    @Column
    @ApiModelProperty(example = "칼로리")
    private Integer kcal;

    @Column
    @ApiModelProperty(example = "주의사항")
    private String warning;

    @Column(nullable = false)
    @ApiModelProperty(example = "재료의 효능")
    private String spec;

    @Column
    @ApiModelProperty(example = "재료 점수")
    private int score;

    @Column(nullable = false)
    @ColumnDefault("'active'")
    @ApiModelProperty(example = "서버 DB에 저장되는 status 정보 (클라이언트 사용x)")
    private String status;



    @Builder

    public Ingredients(String category, String name, String imgUrl, String warning, String spec, int score, int kcal) {
        this.category = category;
        this.name = name;
        this.imgUrl = imgUrl;
        this.warning = warning;
        this.spec = spec;
        this.score = score;
        this.kcal = kcal;
    }

    public void update(String name, String imgUrl,String warning, String spec, int score, int kcal) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.warning = warning;
        this.spec = spec;
        this.score = score;
        this.kcal = kcal;
    }
}
