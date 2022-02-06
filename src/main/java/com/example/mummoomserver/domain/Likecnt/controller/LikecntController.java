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




//    /*
//    게시글을 좋아요 목록에 추가해주는 친구
//    */
//    @GetMapping("/like/{postIdx}")
//    @ApiOperation(value = "좋아하는 게시글 조회",notes = "좋아하는 게시글을 등록해줍니다.")
//    public ResponseTemplate<String> makelikecnt(@PathVariable Long postIdx){
//        String email = userService.getAuthUserEmail();
//        log.info("이메일 {}",email);
//        likecntService.like(postIdx,email);
//
//
//        String result = "좋아하는 게시글 등록에 성공했습니다.";
//        return new ResponseTemplate<>(result);
//    }


    /*
       게시글을 좋아요 목록에 추가해주는 친구
    */
    @PostMapping("/like/{postIdx}")
    @ApiOperation(value = "좋아하는 게시글 조회",notes = "좋아하는 게시글을 등록해줍니다.")
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