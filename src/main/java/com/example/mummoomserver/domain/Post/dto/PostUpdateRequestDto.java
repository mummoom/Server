package com.example.mummoomserver.domain.Post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private String imgUrl;
    private String status;

    @Builder
    public PostUpdateRequestDto(String title, String content, String imgUrl, String status){
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.status = status;
    }
}
