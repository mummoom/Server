package com.example.mummoomserver.domain.Post.dto;

import com.example.mummoomserver.domain.Post.Post;

import com.example.mummoomserver.login.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String imgUrl;


    @Builder
    public PostSaveRequestDto(String title, String content, String imgUrl){
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .imgUrl(imgUrl)
                .build();
    }
}
