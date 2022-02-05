package com.example.mummoomserver.domain.NestedComment;

import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.login.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class NestedComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nestedCommentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentIdx")
    private Comment comment;

    private String content;

    @Builder
    public NestedComment(String content,User user,Comment comment){
        this.content=content;
        this.user = user;
        this.comment = comment;
    }
}