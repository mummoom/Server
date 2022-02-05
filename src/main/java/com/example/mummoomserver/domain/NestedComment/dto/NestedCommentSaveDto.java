package com.example.mummoomserver.domain.NestedComment.dto;

import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.NestedComment.NestedComment;
import com.example.mummoomserver.login.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NestedCommentSaveDto {
    private String content;


    public NestedComment toEntity(){
        return NestedComment.builder()
                .content(content)
                .build();
    }
}
