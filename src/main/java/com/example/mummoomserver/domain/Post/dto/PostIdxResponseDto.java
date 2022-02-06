package com.example.mummoomserver.domain.Post.dto;

import com.example.mummoomserver.domain.Comment.dto.CommentResponseDto;
import com.example.mummoomserver.domain.Post.Post;
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
public class PostIdxResponseDto {
    @ApiModelProperty(example = "게시글 식별자")
    private Long postIdx;

    @ApiModelProperty(example = "이미지 url")
    private String imgUrl;

    @ApiModelProperty(example = "글 제목")
    private String title;

    @ApiModelProperty(example = "글 내용")
    private String content;

    @ApiModelProperty(example = "글 작성자의 유저 식별자")
    private Long userIdx;

    @ApiModelProperty(example = "글 작성자의 닉네임")
    private String userName;

    @ApiModelProperty(example = "글 작성자의 프로필 사진")
    private String userImage;

    @ApiModelProperty(example = "글에 달린 댓글 리스트")
    private List<CommentResponseDto> comments;

    @ApiModelProperty(example = "좋아요 개수")
    private String likecnt;

    @ApiModelProperty(example = "글 작성 시간")
    private LocalDateTime createdAt;

    @Builder
    public PostIdxResponseDto(Post entity){
        this.postIdx = entity.getPostIdx();
        this.imgUrl = entity.getImgUrl();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.userIdx = entity.getUser().getUserIdx();
        this.userName = entity.getUser().getNickName();
        this.userImage = entity.getUser().getImgUrl();
        this.createdAt = entity.getCreateAt();
    }
}