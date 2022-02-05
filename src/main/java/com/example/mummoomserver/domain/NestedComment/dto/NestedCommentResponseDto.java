package com.example.mummoomserver.domain.NestedComment.dto;

import com.example.mummoomserver.domain.NestedComment.NestedComment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NestedCommentResponseDto {
    @ApiModelProperty(example = "대댓글 작성자의 유저 식별자")
    private Long userIdx;

    @ApiModelProperty(example = "대댓글 작성자의 닉네임")
    private String userName;

    @ApiModelProperty(example = "대댓글 내용")
    private String content;

    @ApiModelProperty(example = "대댓글 작성 시간")
    private LocalDateTime createdAt;

    @Builder
    public NestedCommentResponseDto(NestedComment entity) {
        this.userName = entity.getUser().getNickName();
        this.userIdx = entity.getUser().getUserIdx();
        this.content = entity.getContent();
        this.createdAt = entity.getCreateAt();
    }
}

