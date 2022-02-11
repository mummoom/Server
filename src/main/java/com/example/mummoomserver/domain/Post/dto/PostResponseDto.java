package com.example.mummoomserver.domain.Post.dto;

import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.login.users.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    @ApiModelProperty(example = "게시글 식별자")
    private Long postIdx;

    @ApiModelProperty(example = "글 제목")
    private String title;

    @ApiModelProperty(example = "글 내용")
    private String content;

    @ApiModelProperty(example = "이미지 url")
    private String imgUrl;

    @ApiModelProperty(example = "글 작성자의 유저 식별자")
    private Long userIdx;

    @ApiModelProperty(example = "좋아요 개수")
    private String likecnt;


    public PostResponseDto(Post entity){
        this.postIdx = entity.getPostIdx();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.imgUrl = entity.getImgUrl();
        this.userIdx = entity.getUser().getUserIdx();
    }
}
