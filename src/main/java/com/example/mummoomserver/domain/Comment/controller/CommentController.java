package com.example.mummoomserver.domain.Comment.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Comment.dto.CommentSaveRequestDto;
import com.example.mummoomserver.domain.Comment.service.CommentService;
import com.example.mummoomserver.login.users.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @ApiOperation(value="댓글 작성", notes="댓글을 작성합니다. JWT 토큰 입력 필수!")
    @PostMapping("/comment/{postIdx}")
    public ResponseTemplate<Long> save(@PathVariable Long postIdx, @RequestBody CommentSaveRequestDto requestDto) {
        try {
            String email = userService.getAuthUserEmail();
            Long result = commentService.save(postIdx, email, requestDto);
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    @ApiOperation(value="댓글 삭제", notes="댓글을 삭제합니다. JWT 토큰 입력 필수!")
    @DeleteMapping("/comment/{commentIdx}")
    public ResponseTemplate<String> delete(@PathVariable Long commentIdx){
        try{
            String email = userService.getAuthUserEmail();
            commentService.delete(email, commentIdx);
            String result = "댓글 삭제에 성공했습니다.";
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }
}
