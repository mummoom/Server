package com.example.mummoomserver.domain.Report.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Report.dto.ReportSaveRequestDto;
import com.example.mummoomserver.domain.Report.service.ReportService;
import com.example.mummoomserver.login.users.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

    @ApiOperation(value="신고하기", notes="신고 내용을 디비에 저장합니다. JWT 토큰 입력 필수!")
    @PostMapping("/report/{postIdx}")
    @ApiResponses({
            @ApiResponse(code=3000, message="데이터베이스 요청 에러.")
            ,@ApiResponse(code=8000, message="존재하지 않는 게시글 입니다.")
            ,@ApiResponse(code=8001, message="회원정보를 찾을 수 없습니다.")
    })
    public ResponseTemplate<Long> save(@PathVariable Long postIdx, @RequestBody ReportSaveRequestDto requestDto){
        try{
            String email = userService.getAuthUserEmail();
            return new ResponseTemplate<>(reportService.save(email, postIdx, requestDto));
        }catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    @ApiOperation(value="댓글 신고하기", notes="신고 내용을 디비에 저장합니다. JWT 토큰 입력 필수!")
    @PostMapping("/report/comment/{commentIdx}")
    @ApiResponses({
            @ApiResponse(code=3000, message="데이터베이스 요청 에러.")
            ,@ApiResponse(code=8001, message="회원정보를 찾을 수 없습니다.")
            ,@ApiResponse(code=8002, message="존재하지 않는 댓글 입니다.")
    })
    public ResponseTemplate<Long> saveComment(@PathVariable Long commentIdx, @RequestBody ReportSaveRequestDto requestDto){
        try{
            String email = userService.getAuthUserEmail();
            return new ResponseTemplate<>(reportService.saveComment(email, commentIdx, requestDto));
        }catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    @ApiOperation(value="유저 신고하기", notes="신고내용을 디비에 저장합니다. JWT 토큰 입력 필수!")
    @PostMapping("/report/user/{postIdx}")
    @ApiResponses({
            @ApiResponse(code=3000, message="데이터베이스 요청 에러.")
            ,@ApiResponse(code=8000, message="존재하지 않는 게시글 입니다.")
            ,@ApiResponse(code=8001, message="회원정모를 찾을 수 없습니다.")
    })
    public ResponseTemplate<Long> saveUser(@PathVariable Long postIdx, @RequestBody ReportSaveRequestDto requestDto){
        try{
            String email = userService.getAuthUserEmail();
            return new ResponseTemplate<>(reportService.saveUser(email, postIdx, requestDto));
        } catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    @ApiOperation(value="유저 차단하기", notes="유저를 차단하면 그 유저의 모든 게시글이 안보이게 됩니다.")
    @PostMapping("/block/{reportIdx}")
    @ApiResponses({
            @ApiResponse(code=8000, message="존재하지 않는 게시글입니다.")
            ,@ApiResponse(code=8001, message="회원정보를 찾을 수 없습니다.")
    })
    public ResponseTemplate<String> blockUser(@PathVariable Long reportIdx){
        try{
            reportService.blockUser(reportIdx);
            String result = "차단에 성공했습니다.";
            return new ResponseTemplate<>(result);
        } catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }
}
