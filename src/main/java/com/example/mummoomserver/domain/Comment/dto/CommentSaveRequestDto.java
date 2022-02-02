package com.example.mummoomserver.domain.Comment.dto;


import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.login.users.User;
import lombok.Builder;

public class CommentSaveRequestDto {
    private Post postIdx;
    private User userIdx;
    private String content;

    @Builder
    public CommentSaveRequestDto(String content){
        this.content = content;
    }

    public Comment toEntity(){
        return Comment.builder()
                .content(content)
                .build();
    }
}
