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
}
