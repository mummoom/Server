package com.example.mummoomserver.domain.Comment.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Comment.dto.CommentSaveRequestDto;
import com.example.mummoomserver.domain.Comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{postIdx}/{userIdx}")
    public ResponseTemplate<Long> commentSave(@PathVariable Long postIdx, @PathVariable Long userIdx, @RequestBody CommentSaveRequestDto requestDto) {
        try {
            Long result = commentService.save(postIdx, userIdx, requestDto);
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/comment/{commentIdx}") // 왜 유저도 같이 지워지지 고쳣습니다
    public ResponseTemplate<String> commentDelete(@PathVariable Long commentIdx){
        try{
            commentService.delete(commentIdx);
            String result = "댓글 삭제에 성공했습니다.";
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }
}
