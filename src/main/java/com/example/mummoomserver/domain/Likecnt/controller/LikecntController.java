package com.example.mummoomserver.domain.Likecnt.controller;

import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Likecnt.dto.LikecntDto;
import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import com.example.mummoomserver.domain.Likecnt.service.LikecntService;
import com.example.mummoomserver.domain.Post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class LikecntController {
    private final LikecntService likecntService;
    //private final JwtProvider provider;

    @GetMapping("/post/{postIdx}")//postIdx 를 불러옴
    public LikecntDto findByPostIdx(@PathVariable Long postIdx){
        return likecntService.findByIdx(postIdx);
    }
    @GetMapping("/user/{userIdx}")
    public LikecntDto findByUserIdx(@PathVariable Long userIdx){
        return likecntService.findByIdx(userIdx);
    }
    @PostMapping("/like/{postIdx}")
    public ResponseTemplate<String> makelikecnt(@PathVariable Long postIdx){

        return ???;
    }
    @DeleteMapping("/unlike/{postIdx}")
    public ResponseTemplate<String> deletelikecnt(@PathVariable Long postIdx){

        return ???;
    }


}
