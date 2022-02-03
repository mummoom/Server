package com.example.mummoomserver.domain.Comment.dto;


import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.login.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveRequestDto {
    private Post postIdx;
    private User userIdx;
    private String content;

    @Builder
    public CommentSaveRequestDto(Post postIdx, User userIdx, String content){
        this.content = content;
        this.postIdx = postIdx;
        this.userIdx = userIdx;
    }

    public Comment toEntity(){
        return Comment.builder()
                .userIdx(userIdx)
                .postIdx(postIdx)
                .content(content)
                .build();
    }
}
