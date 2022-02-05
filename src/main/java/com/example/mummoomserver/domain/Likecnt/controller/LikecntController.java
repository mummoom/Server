package com.example.mummoomserver.domain.Likecnt.controller;

import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Likecnt.dto.LikecntDto;
import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import com.example.mummoomserver.domain.Likecnt.service.LikecntService;
import com.example.mummoomserver.domain.Post.dto.PostResponseDto;
import com.example.mummoomserver.login.users.service.UserServiceImpl;
import io.swagger.annotations.*;
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
    @ApiOperation(value = "게시글 조회",notes = "게시글 Idx를 불러옵니다.")
    @ApiImplicitParam(name = "postIdx", value = "게시글 Idx")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 301,message = "토큰이 존재하지 않습니다."),
            @ApiResponse(code = 302,message = "토큰값이 유효하지 않습니다."),
            @ApiResponse(code = 303,message = "키를 입력해주세요."),
            @ApiResponse(code = 304,message = "키는 숫자로 입력해주어야 합니다."),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "sql 에러입니다.")

    })
    public LikecntDto findByPostIdx(@PathVariable Long postIdx){
        return likecntService.findByIdx(postIdx);
    }


    /*
    사용자의 이메일과 닉네임을 불러오는 친구
    */
    @PostMapping("/user/{userIdx}")
    @ApiOperation(value = "사용자 이메일,닉네임 조회",notes = "사용자 이메일과 닉네임을 불러옵니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "유저 이메일",required = true),
            @ApiImplicitParam(name = "nickname", value = "유저 닉네임",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 301,message = "토큰이 존재하지 않습니다."),
            @ApiResponse(code = 302,message = "토큰값이 유효하지 않습니다."),
            @ApiResponse(code = 303,message = "키를 입력해주세요."),
            @ApiResponse(code = 304,message = "키는 숫자로 입력해주어야 합니다."),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "sql 에러입니다.")

    })
    public void findByUserIdx(@RequestParam Long userIdx){
        userService.getAuthUserEmail();
        userService.getAuthUserNickname();
    }



    /*
    게시글을 좋아요 목록에 추가해주는 친구
    */
    @PostMapping("/like/{postIdx}")
    @ApiOperation(value = "좋아하는 게시글 조회",notes = "좋아하는 게시글 Idx를 불러옵니다.")
    @ApiImplicitParam(name = "like",value = "게시글Idx 좋아요 등록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 301,message = "토큰이 존재하지 않습니다."),
            @ApiResponse(code = 302,message = "토큰값이 유효하지 않습니다."),
            @ApiResponse(code = 303,message = "키를 입력해주세요."),
            @ApiResponse(code = 304,message = "키는 숫자로 입력해주어야 합니다."),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "sql 에러입니다.")

    })
    public ResponseTemplate<String> makelikecnt(@PathVariable Long postIdx){
        likecntService.like(userService.getAuthUserNickname(),postIdx);
        return makelikecnt(postIdx);
    }


    /*
    게시글을 좋아요 목록에서 삭제해주는 친구
    */
    @ApiOperation(value = "게시글 좋아요 취소",notes = "게시글 Idx를 좋아요 목록에서 삭제해줍니다.")
    @DeleteMapping("/unlike/{postIdx}")
    @ApiImplicitParam(name = "unlike",value = "게시글Idx 좋아요 취소")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 301,message = "토큰이 존재하지 않습니다."),
            @ApiResponse(code = 302,message = "토큰값이 유효하지 않습니다."),
            @ApiResponse(code = 303,message = "키를 입력해주세요."),
            @ApiResponse(code = 304,message = "키는 숫자로 입력해주어야 합니다."),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "sql 에러입니다.")

    })
    public ResponseTemplate<String> deletelikecnt(@PathVariable Long postIdx){

        return deletelikecnt(postIdx);
    }


}
