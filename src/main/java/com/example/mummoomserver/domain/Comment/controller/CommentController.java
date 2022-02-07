package com.example.mummoomserver.domain.Comment.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Comment.dto.CommentSaveRequestDto;
import com.example.mummoomserver.domain.Comment.service.CommentService;
import com.example.mummoomserver.login.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(code=3000, message="데이터베이스 요청 에러.")
            ,@ApiResponse(code=8000, message="존재하지 않는 게시글 입니다.")
            ,@ApiResponse(code=8001, message="회원정보를 찾을 수 없습니다.")
    })
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
    @ApiResponses({
            @ApiResponse(code=8001, message="회원정보를 찾을 수 없습니다.")
            ,@ApiResponse(code=8002, message="존재하지 않는 댓글 입니다.")
            ,@ApiResponse(code=8004, message="작성자만 사용할 수 있습니다.")
    })
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
