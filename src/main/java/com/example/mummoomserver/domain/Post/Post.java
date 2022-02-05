package com.example.mummoomserver.domain.Post;
import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.config.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@DynamicInsert
@Table(name="Post")
@Entity
public class Post extends BaseTimeEntity { // extends BaseTimeEntity  basetimeentity 클래스를 Post 가 상속받는다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postIdx;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Column
    private String imgUrl;

    @Column
    private String status;

    @Builder
    public Post(String title, String content, User user, String imgUrl, String status) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.imgUrl = imgUrl;
        this.status = status;
    }

    public void update(String title, String content, String imgUrl) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}