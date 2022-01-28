package com.example.mummoomserver.domain.Post.dto;

import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.User.User;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long postIdx;
    private String title;
    private String content;
    private String imgUrl;
    private User userIdx;

    public PostResponseDto(Post entity){
        this.postIdx = entity.getPostIdx();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.imgUrl = entity.getImgUrl();
        this.userIdx = entity.getUserIdx();
    }
}
