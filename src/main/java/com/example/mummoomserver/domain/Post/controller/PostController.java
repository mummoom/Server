package com.example.mummoomserver.domain.Post.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Post.dto.PostResponseDto;
import com.example.mummoomserver.domain.Post.dto.PostSaveRequestDto;
import com.example.mummoomserver.domain.Post.dto.PostUpdateRequestDto;
import com.example.mummoomserver.domain.Post.service.PostService;
import com.example.mummoomserver.login.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final JwtProvider jwtProvider;
    private final PostService postService;


    @PostMapping("/post")
    public ResponseTemplate<Long> save(@RequestBody @ApiIgnore PostSaveRequestDto requestDto) {
        try{
            String userName = jwtProvider.getUserName();
            Long result = postService.save(userName, requestDto);
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/post/{postIdx}")
    public ResponseTemplate<String> update(@PathVariable Long postIdx, @RequestBody PostUpdateRequestDto requestDto){
        try {
            postService.update(postIdx, requestDto);
            String result = "게시글 수정에 성공했습니다.";
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/post/{postIdx}")
    public ResponseTemplate<PostResponseDto> findByPostIdx(@PathVariable Long postIdx){
        try {
            PostResponseDto result = postService.findByPostIdx(postIdx);
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts")
    public ResponseTemplate<List<PostResponseDto>> getPosts(){
        try{
            return new ResponseTemplate<>(postService.getPosts());
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/post/{postIdx}")
    public ResponseTemplate<String> delete(@PathVariable Long postIdx){
        try{
            postService.delete(postIdx);
            String result = "게시글 삭제에 성공했습니다.";
            return new ResponseTemplate<>(result);
        } catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

}
