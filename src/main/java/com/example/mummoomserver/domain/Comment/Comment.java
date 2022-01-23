package com.example.mummoomserver.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
// @Table(name="") 테이블 이름 명시
// fk 가져오기 방법 적용해보기
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentIdx;

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
