package com.example.mummoomserver.domain.Comment.dto;

import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.NestedComment.dto.NestedCommentResponseDto;
import com.example.mummoomserver.login.users.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    @ApiModelProperty(example = "댓글 식별자")
    private Long commentIdx;
    
    @ApiModelProperty(example = "댓글 작성자의 유저 식별자")
    private Long userIdx;
    
    @ApiModelProperty(example = "댓글 작성자의 닉네임")
    private String nickName;
    
    @ApiModelProperty(example = "댓글 내용")
    private String content;
    
    @ApiModelProperty(example = "댓글에 달린 대댓글 리스트")
    private List<NestedCommentResponseDto> nestedComments;

    @ApiModelProperty(example = "댓글 작성 시간")
    private LocalDateTime createdAt;

    @Builder
    public CommentResponseDto(Comment entity) {
        this.commentIdx = entity.getCommentIdx();
        this.nickName = entity.getUser().getNickName();
        this.userIdx = entity.getUser().getUserIdx();
        this.content = entity.getContent();
        this.createdAt = entity.getCreateAt();
    }
}

