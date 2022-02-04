package com.example.mummoomserver.domain.Comment;
import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.domain.Post.Post;

import com.example.mummoomserver.login.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="postIdx")
    private Post postIdx;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="userIdx")
    private User userIdx;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    @ColumnDefault("'active'")
    private String status;

    @Builder
    public Comment(Post postIdx, User userIdx, String content, String status){
        this.postIdx = postIdx;
        this.userIdx = userIdx;
        this.content = content;
        this.status = status;
    }
}
