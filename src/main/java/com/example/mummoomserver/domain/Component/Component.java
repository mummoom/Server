package com.example.mummoomserver.domain.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity

public class Component { //extends
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long componentIdx;

    @Column
    private int kcal;

    @Column
    private float dan;

    @Column
    private float tan;

    @Column
    private float gi;

    @Column
    private float mu;

    @Column
    private float water;

    @Column(columnDefinition = "TEXT")
    private String effect;

    @Column
    private String status;

    @Builder
    public Component(int kcal, float dan, float tan, float gi, float mu, float water, String effect, String status) {
        this.kcal = kcal;
        this.dan = dan;
        this.tan = tan;
        this.gi = gi;
        this.mu = mu;
        this.water = water;
        this.effect = effect;
        this.status = status;
    }
}
