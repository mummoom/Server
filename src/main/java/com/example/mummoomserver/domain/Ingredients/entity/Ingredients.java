package com.example.mummoomserver.domain.Ingredients.entity;

import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.domain.Component.entity.Component;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="Ingredients")
public class Ingredients extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientsIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="componentIdx") // fk 이름 component_idx 로 설정됨
    @JsonBackReference
    private Component component;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String imgUrl;
    @Column
    private Integer kcal;
    @Column
    private String warning;
    @Column(nullable = false)
    private String spec;
    @Column
    private int score;
    @Column(nullable = false)
    @ColumnDefault("'active'")
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
    }
}
