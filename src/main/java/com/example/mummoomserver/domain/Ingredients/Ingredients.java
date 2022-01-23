package com.example.mummoomserver.domain.Ingredients;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity

public class Ingredients { //exends basetimeentity
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

    @Builder
    public Ingredients(String category,String name, String imgUrl,String warning, String spec, int score) {
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
