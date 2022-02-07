package com.example.mummoomserver.domain.Component.entity;

import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.domain.Ingredients.entity.Ingredients;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Component extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long componentIdx;

    @OneToMany(mappedBy = "component")
    @JsonBackReference
    List<Ingredients> ingredients = new ArrayList<>();

    @Column(nullable = false)
    @ApiModelProperty(example = "단백질(g)")
    private float dan;

    @ApiModelProperty(example = "탄수화물(g)")
    @Column(nullable = false)
    private float tan;

    @ApiModelProperty(example = "지방(g)")
    @Column(nullable = false)
    private float gi;


    @ApiModelProperty(example = "무기질(g)")
    @Column(nullable = false)
    private float mu;

    @ApiModelProperty(example = "물(g)")
    @Column(nullable = false)
    private float water;


    @ApiModelProperty(example = "재료의 효과(g)")
    @Column(columnDefinition = "TEXT")
    private String effect;

    @Column(nullable = false)
    @ColumnDefault("'active'")
    private String status;

    @Builder
    public Component(float dan, float tan, float gi, float mu, float water, String effect, String status) {
        this.dan = dan;
        this.tan = tan;
        this.gi = gi;
        this.mu = mu;
        this.water = water;
        this.effect = effect;
        this.status = status;
    }
}
