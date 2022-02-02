package com.example.mummoomserver.domain.Comment;
import com.example.mummoomserver.domain.Post.Post;

import com.example.mummoomserver.login.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
// @Table(name="Comment")
@Entity
// @Table(name="") 테이블 이름 명시
// fk 가져오기 방법 적용해보기
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentIdx;

    @ManyToOne
    @JoinColumn(name="postIdx")
    private Post postIdx;

    @ManyToOne
    @JoinColumn(name="userIdx")
    private User userIdx;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
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
