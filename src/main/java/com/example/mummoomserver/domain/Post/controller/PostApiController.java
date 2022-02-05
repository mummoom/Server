package com.example.mummoomserver.domain.Post.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Post.dto.PostIdxResponseDto;
import com.example.mummoomserver.domain.Post.dto.PostResponseDto;
import com.example.mummoomserver.domain.Post.dto.PostSaveRequestDto;
import com.example.mummoomserver.domain.Post.dto.PostUpdateRequestDto;
import com.example.mummoomserver.domain.Post.service.PostService;
import com.example.mummoomserver.login.users.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;
    private final UserService userService;

    @ApiOperation(value="게시글 등록", notes="게시글을 등록합니다. JWT 토큰 입력 필수!")
    @PostMapping("/post")
    public ResponseTemplate<Long> save(@RequestBody PostSaveRequestDto requestDto) {
        try{
            String email = userService.getAuthUserEmail();
            Long result = postService.save(email, requestDto);
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    @ApiOperation(value="게시글 수정", notes="게시글을 수정합니다. JWT 토큰 입력 필수!")
    @ApiImplicitParam(name="postIdx", value="게시글 식별자")
    @PatchMapping("/post/{postIdx}")
    public ResponseTemplate<String> update(@PathVariable Long postIdx, @RequestBody PostUpdateRequestDto requestDto){
        try {
            String email = userService.getAuthUserEmail();
            String result = "게시글 수정에 성공했습니다.";
            postService.update(postIdx, email, requestDto);
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    @ApiOperation(value="게시글 상세 정보 조회", notes="게시글 상세 정보를 조회합니다.")
    @GetMapping("/post/{postIdx}")
    public ResponseTemplate<PostIdxResponseDto> findByPostIdx(@PathVariable Long postIdx){
        try {
            PostIdxResponseDto result = postService.findByPostIdx(postIdx);
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    @ApiOperation(value="게시글 전체 조회", notes="전체 게시글을 조회합니다.")
    @GetMapping("/posts")
    public ResponseTemplate<List<PostResponseDto>> getPosts(){
        try{
            return new ResponseTemplate<>(postService.getPosts());
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    @ApiOperation(value="게시글 삭제", notes="게시글을 삭제합니다. JWT 토큰 입력 필수!")
    @DeleteMapping("/post/{postIdx}")
    public ResponseTemplate<String> delete(@PathVariable Long postIdx){
        try{
            String email = userService.getAuthUserEmail();
            postService.delete(email, postIdx);
            String result = "게시글 삭제에 성공했습니다.";
            return new ResponseTemplate<>(result);
        } catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

}