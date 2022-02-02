package com.example.mummoomserver.domain.Post.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Post.dto.PostResponseDto;
import com.example.mummoomserver.domain.Post.dto.PostSaveRequestDto;
import com.example.mummoomserver.domain.Post.dto.PostUpdateRequestDto;
import com.example.mummoomserver.domain.Post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("/post/save")
    public ResponseTemplate<String> save(@RequestBody PostSaveRequestDto requestDto){
        try{
            postService.save(requestDto);
            String result = "게시글 등록에 성공했습니다.";
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/post/{postIdx}")
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

}
