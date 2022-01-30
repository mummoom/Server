package com.example.mummoomserver.domain.Post.controller;

import com.example.mummoomserver.domain.Post.dto.PostResponseDto;
import com.example.mummoomserver.domain.Post.dto.PostSaveRequestDto;
import com.example.mummoomserver.domain.Post.dto.PostUpdateRequestDto;
import com.example.mummoomserver.domain.Post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;

    @PostMapping("/post/save")
    public Long save(@RequestBody PostSaveRequestDto requestDto){
        return postService.save(requestDto);
    }

    @PutMapping("/post/{postIdx}")
    public Long update(@PathVariable Long postIdx, @RequestBody PostUpdateRequestDto requestDto){
        return postService.update(postIdx, requestDto);
    }

    @GetMapping("/post/{postIdx}")
    public PostResponseDto findByPostIdx(@PathVariable Long postIdx){
        return postService.findByPostIdx(postIdx);
    }


}
