package com.example.mummoomserver.domain.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity

public class Post { // extends BaseTimeEntity  basetimeentity 클래스를 Post 가 상속받는다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postIdx;

    @Column(length = 500, nullable = false)
    private String title;

    @Column
    private int likeCnt;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private int userIdx;

    @Column
    private String imgUrl;

    @Column
    private String status;

    @Builder
    public Post(String title, int likeCnt, String content, int userIdx, String imgUrl, String status) {
        this.title = title;
        this.likeCnt = likeCnt;
        this.content = content;
        this.userIdx = userIdx;
        this.imgUrl = imgUrl;
        this.status = status;
    }

    public void update(String title, String content, String imgUrl, String status) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.status = status;
    }
}