package com.example.mummoomserver.domain.Ingredients.entity;

import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.domain.Component.entity.Component;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Ingredient extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientsIdx;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private String imgUrl;

    @Column
    private String warning;

    @Column
    private String spec;

    @Column
    private int score;

    @Column
    @ColumnDefault("'active'")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="componentIdx") // fk 이름 componentIdx 로 설정됨
    private Component component;


    @Builder
    public Ingredient(String category, String name, String imgUrl, String warning, String spec, int score) {
        this.category = category;
        this.name = name;
        this.imgUrl = imgUrl;
        this.warning = warning;
        this.spec = spec;
        this.score = score;
    }

    public void update(String name, String imgUrl,String warning, String spec, int score) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.warning = warning;
        this.spec = spec;
        this.score = score;
    }
}
