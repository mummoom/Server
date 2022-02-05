package com.example.mummoomserver.domain.Comment;
import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.domain.NestedComment.NestedComment;
import com.example.mummoomserver.domain.Post.Post;

import com.example.mummoomserver.login.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name="comment")
@Entity
// @Table(name="") 테이블 이름 명시
// fk 가져오기 방법 적용해보기
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="postIdx")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userIdx")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    @ColumnDefault("'active'")
    private String status;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<NestedComment> nestedComments = new ArrayList<>();

    @Builder
    public Comment(Post post, User user, String content, String status){
        this.post = post;
        this.user = user;
        this.content = content;
        this.status = status;
    }

    public void update(String content, User user, String status){
        this.content = content;
        this.user = user;
        this.status = status;
    }
}
