package com.example.mummoomserver.domain.NestedComment.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.NestedComment.dto.NestedCommentSaveDto;
import com.example.mummoomserver.domain.NestedComment.service.NestedCommentService;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import com.example.mummoomserver.login.users.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NestedCommentController {
    private final NestedCommentService nestedCommentService;
    private final UserService userService;

    @ApiOperation(value="대댓글 작성", notes="대댓글을 작성합니다. JWT 토큰 입력 필수!")
    @PostMapping("/comments/nestedComment/{commentIdx}")
    public ResponseTemplate<Long> saveNestedComment(@RequestBody NestedCommentSaveDto requestDto,
                                                @PathVariable Long commentIdx) throws ResponeException {
        try {
            String email = userService.getAuthUserEmail();
            Long result = nestedCommentService.save(email, commentIdx, requestDto);
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    @ApiOperation(value="대댓글 삭제", notes="대댓글을 삭제합니다. JWT 토큰 입력 필수!")
    @DeleteMapping("/comments/nestedComment/{nestedCommentIdx}")
    public ResponseTemplate<String> delete(@PathVariable Long nestedCommentIdx){
        try{
            String email = userService.getAuthUserEmail();
            nestedCommentService.delete(email, nestedCommentIdx);
            String result = "대댓글 삭제에 성공했습니다.";
            return new ResponseTemplate<>(result);
        } catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }
}
