package com.example.mummoomserver.domain.Likecnt.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Likecnt.dto.LikecntDto;
import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import com.example.mummoomserver.domain.Likecnt.service.LikecntService;
import com.example.mummoomserver.domain.Post.service.PostService;
import com.example.mummoomserver.login.users.service.UserServiceImpl;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class LikecntController {
    private final LikecntService likecntService;
    private final UserServiceImpl userService;


    /*
       게시글을 좋아요 목록에 추가,삭제 해주는 친구
    */
    @ApiImplicitParam(
            name = "postIdx"
            , value = "게시글 Idx 즉 게시글 생성 후 나오는 data 값을 입력해주시면 됩니다 !"
            , paramType = "path"
            , defaultValue = "None"
    )
    @ApiOperation(value = "좋아하는 게시글 등록,삭제",notes = "좋아하는 게시글을 등록(data = true),삭제(data = false) 해줍니다.")
    @PostMapping("/like/{postIdx}")
    public ResponseTemplate<Boolean> postLike(@PathVariable long postIdx){
        try {
            String email = userService.getAuthUserEmail();
            boolean result = likecntService.postLike(email, postIdx);
            return new ResponseTemplate<>(result);
        }catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }


}