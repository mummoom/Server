package com.example.mummoomserver.domain.Likecnt.dto;


import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import com.example.mummoomserver.domain.Post.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikecntDto {
    private Post postIdx;

    public LikecntDto(Likecnt entity) {
    this.postIdx = entity.getPostIdx();
    }
}
