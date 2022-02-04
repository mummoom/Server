package com.example.mummoomserver.domain.Post.dto;

import com.example.mummoomserver.domain.Post.Post;

import com.example.mummoomserver.login.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private User userIdx;
    private String imgUrl;


    @Builder
    public PostSaveRequestDto(String title, String content, User userIdx, String imgUrl){
        this.title = title;
        this.content = content;
        this.userIdx = userIdx;   // 나중에 주석 풀 예정
        this.imgUrl = imgUrl;
    }

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .userIdx(userIdx)
                .imgUrl(imgUrl)
                .build();
    }
}
