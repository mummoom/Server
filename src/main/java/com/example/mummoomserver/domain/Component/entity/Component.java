package com.example.mummoomserver.domain.Component.entity;

import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.domain.Ingredients.entity.Ingredients;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @JsonManagedReference
    List<Ingredients> ingredients = new ArrayList<>();

    @Column(nullable = false)
    private float dan;

    @Column(nullable = false)
    private float tan;

    @Column(nullable = false)
    private float gi;

    @Column(nullable = false)
    private float mu;

    @Column(nullable = false)
    private float water;

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
