package com.example.mummoomserver.domain.Likecnt.controller;

import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Likecnt.dto.LikecntDto;
import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import com.example.mummoomserver.domain.Likecnt.service.LikecntService;
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
    게시글을 좋아요 목록에 추가해주는 친구
    */
    @GetMapping("/like/{postIdx}")
    @ApiOperation(value = "좋아하는 게시글 조회",notes = "좋아하는 게시글을 등록해줍니다.")
    public ResponseTemplate<String> makelikecnt(@PathVariable Long postIdx){
        String email = userService.getAuthUserEmail();
        log.info("이메일 {}",email);
        likecntService.like(postIdx,email);


        String result = "좋아하는 게시글 등록에 성공했습니다.";
        return new ResponseTemplate<>(result);
    }



    /*
    게시글을 좋아요 목록에서 삭제해주는 친구
    */
    @ApiOperation(value = "게시글 좋아요 취소",notes = "게시글 Idx를 좋아요 목록에서 삭제해줍니다.")
    @DeleteMapping("/unlike/{postIdx}")
    public ResponseTemplate<String> deletelikecnt(@PathVariable Long postIdx){
        String email = userService.getAuthUserEmail();
        likecntService.unlike(postIdx,email);


        String result = "좋아하는 게시글 삭제에 성공했습니다.";
        return new ResponseTemplate<>(result);
    }


}