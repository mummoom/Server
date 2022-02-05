package com.example.mummoomserver.domain.Comment.dto;


import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.login.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentSaveRequestDto {
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
