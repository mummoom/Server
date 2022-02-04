package com.example.mummoomserver.domain.Likecnt.controller;

import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Likecnt.dto.LikecntDto;
import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import com.example.mummoomserver.domain.Likecnt.service.LikecntService;
import com.example.mummoomserver.domain.Post.dto.PostResponseDto;
import com.example.mummoomserver.login.users.service.UserServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class LikecntController {
    private final LikecntService likecntService;
    private UserServiceImpl userService;

    /*
    게시글의 Idx를 불러오는 친구
    */
    @GetMapping("/post/{postIdx}")
    @ApiImplicitParam(name = "postIdx", value = "게시글 Idx")
    public LikecntDto findByPostIdx(@PathVariable Long postIdx){
        return likecntService.findByIdx(postIdx);
    }


    /*
    사용자의 이메일과 닉네임을 불러오는 친구
    */
    @PostMapping("/user/{userIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "유저 이메일",required = true),
            @ApiImplicitParam(name = "nickname", value = "유저 닉네임",required = true)
    })
    public void findByUserIdx(@RequestParam Long userIdx){
        userService.getAuthUserEmail();
        userService.getAuthUserNickname();
    }



    /*
    게시글을 좋아요 목록에 추가해주는 친구
    */
    @PostMapping("/like/{postIdx}")
    @ApiImplicitParam(name = "like",value = "게시글Idx 좋아요 등록")
    public ResponseTemplate<String> makelikecnt(@PathVariable Long postIdx){
        likecntService.like(userService.getAuthUserNickname(),postIdx);
        return makelikecnt(postIdx);
    }


    /*
    게시글을 좋아요 목록에서 삭제해주는 친구
    */
    @DeleteMapping("/unlike/{postIdx}")
    @ApiImplicitParam(name = "unlike",value = "게시글Idx 좋아요 취소")
    public ResponseTemplate<String> deletelikecnt(@PathVariable Long postIdx){

        return deletelikecnt(postIdx);
    }


}
