package com.example.mummoomserver.domain.Comment.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Comment.dto.CommentSaveRequestDto;
import com.example.mummoomserver.domain.Comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/save")
    public ResponseTemplate<String> commentSave(@RequestBody CommentSaveRequestDto requestDto) {
        try {
            commentService.save(requestDto);
            String result = "댓글 등록에 성공했습니다.";
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }
}
