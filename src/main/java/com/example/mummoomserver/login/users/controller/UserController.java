package com.example.mummoomserver.login.users.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus;
import com.example.mummoomserver.domain.Dog.entity.Dog;
import com.example.mummoomserver.domain.Dog.repository.DogRepository;
import com.example.mummoomserver.login.token.jwt.JwtProvider;


import com.example.mummoomserver.login.service.UserDetailsImpl;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import com.example.mummoomserver.login.users.dto.LoginDto;
import com.example.mummoomserver.login.users.dto.UserDto;
import com.example.mummoomserver.login.users.requestResponse.*;
import com.example.mummoomserver.login.users.service.UserService;
import com.example.mummoomserver.login.users.service.UserServiceImpl;
import com.example.mummoomserver.login.validation.ValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.INVALID_EMAIL;
import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.INVALID_PASSWORD;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DogRepository dogRepository;

    // 회원가입
    @ApiOperation(value = "회원가입 API", notes = "회원정보(이메일,닉네임,비밀번호)를 모두 필수로 받습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스 에러"),
            @ApiResponse(code = 7003, message = "이메일을 입력해주세요."),
            @ApiResponse(code = 7004, message = "비밀번호를 입력해주세요."),
            @ApiResponse(code = 7005, message = "닉네임을 입력해주세요")})
    @PostMapping("/signup")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "nickName", value = "1~20자를 입력받으며 중복이 되지 않습니다."),
    @ApiImplicitParam(name = "paassword", value = "6~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다.")})

    public ResponseTemplate<?> signUpNewUser(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult) {
        if(signUpRequest.getEmail()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_EMAIL);
        if(signUpRequest.getPassword()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_PASSWORD);
        if(signUpRequest.getNickName()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_NICKNAME);


        //유효성 검사
        if (bindingResult.hasErrors()) throw new ValidationException("회원가입 유효성 검사 실패.", bindingResult.getFieldErrors());
        //성공 시
        userService.saveUser(signUpRequest);
        return new ResponseTemplate("회원가입 성공");
    }

    // 로그인

    /**
     * 현석 로그인 이후 헤더에 토큰 삽입 하도록 수정
     * 로그인할때는 이메일과 비밀번호로 로그인하는걸로 수정
     */
    @ApiOperation(value = "로그인 API", notes = "이메일, 비밀번호로 입력을 합니다. \n"+
                                                "성공 시 response 바디에 X-AUTH-TOKEN 이라는 이름으로 토큰 정보가 반환되고,\n"+
                                                "등록한 강아지가 존재할 경우 true, 존재하지 않을 경우 false")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스 에러"),
            @ApiResponse(code = 7003, message = "이메일을 입력해주세요."),
            @ApiResponse(code = 7004, message = "비밀번호를 입력해주세요."),
            @ApiResponse(code = 7006, message = "이메일이 올바르지 않습니다."),
            @ApiResponse(code = 7007, message = "비밀번호가 올바르지 않습니다.")})
    @PostMapping("/login")
    public ResponseTemplate<LoginDto> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws IOException, ResponeException {
        if(loginRequest.getEmail()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_EMAIL);
        if(loginRequest.getPassword()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_PASSWORD);
        //실패시
        User member = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new ResponeException(INVALID_EMAIL));
        //비밀번호 틀릴 시
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new ResponeException(INVALID_PASSWORD);
        }
        // 로그인 성공 시  // 유저 이메일을 삽입
        String token = jwtProvider.createToken(member.getEmail(), member.getRole());
        jwtProvider.writeTokenResponse(httpServletResponse, token);

        //유저인덱스와 연결된 강아지 정보가 있다면 return true, 아니면 false
        boolean dog_exist = dogRepository.existsByUser_userIdx(member.getUserIdx());
        LoginDto loginDto = new LoginDto(token, dog_exist);

        return new ResponseTemplate<>(loginDto);
    }

    //토큰 유효성 검사
    @ApiOperation(value = "토큰 유효성 검사 API", notes = "토큰의 유효성을 확인합니다. 유효하다면 true, 유효하지 않다면 false")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스 에러")})
    @GetMapping("/validtoken")
    public ResponseTemplate<?> checkValid(HttpServletRequest request) {
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        boolean result = jwtProvider.validateToken(token);
        return new ResponseTemplate<>(result);
    }

    /**
     * 재현 원래는 필요 없지만 api로 사용해야 할 경우 이걸 응용해 사용
     */
//    @ApiOperation(value = "로그아웃 API")
//    @GetMapping("/user/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        ResponseTemplate<>("로그아웃이 완료되었습니다");
//        return "redirect:/";
//    }

    //프로필 자신 조회
    @ApiOperation(value = "내정보 조회 API", notes = "이메일, 비밀번호,닉네임, 프로필 이미지를 조회할 수 있습니다. jwt 토큰을 입력해주어야 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스에러")})
    @GetMapping("/me")
    public ResponseTemplate<UserDto> getMyProfile() {
        try{
            String email = userService.getAuthUserEmail();
            UserDto userResponse = userService.getUserProfile(email);
            return new ResponseTemplate<>(userResponse);

        }catch(
                ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    // 프로필 수정 사항 반영
    @ApiOperation(value = "내정보 수정 API", notes = "비밀번호,닉네임 변경이 가능하고 프로필 이미지를 여기에서 등록할 수 있습니다. jwt토큰을 입력해주어야 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스에러"),
            @ApiResponse(code = 7002, message = "변경할 닉네임이나 이미지를 입력해주세요"),
            @ApiResponse(code = 7007, message = "비밀번호가 올바르지 않습니다.")})
    @PatchMapping("/me")
    public ResponseTemplate<String> updateProfile(@RequestBody @Valid  UpdateProfileRequest updateProfileRequest) {
        //입력해준 값이 없을 때의 예외 처리
        if ((updateProfileRequest.getNickName() == null) & (updateProfileRequest.getImgUrl() == null))
            return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_UPDATE);
        try {
            String email = userService.getAuthUserEmail();
            userServiceImpl.updateProfile(email, updateProfileRequest);

            String result = "회원 정보 수정에 성공했습니다.";
            return new ResponseTemplate<>("result");

        } catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    // 비밀번호 수정
    @ApiOperation(value = "비밀번호 수정 API", notes = "비밀번호를 확인한 후에 변경이 가능합니다. jwt토큰을 입력해주어야 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스에러"),
            @ApiResponse(code = 7001, message = "변경할 비밀번호를 입력해주세요"),
    })
    @PutMapping("/pwd")
    public ResponseTemplate<String> updatePwd(@RequestBody @Valid UpdatePwdRequest updatePwdRequest) {
        //입력해준 값이 없을 때의 예외 처리
        if (updatePwdRequest.getPassword() == null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_UPDATE_PASSWORD);
        try {
            String email = userService.getAuthUserEmail();
             // 해당하는 유저의 정보를 가져왔음

            userServiceImpl.updateUserPwd(email, updatePwdRequest); // 동일하다는 내용을 확인 됐다면 업데이트 진행
            String result = "회원 비밀번호 수정에 성공했습니다.";

            return new ResponseTemplate<>(result);

        } catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

     //회원 탈퇴ㅣ?
    @DeleteMapping("/withdraw")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스에러"),
            @ApiResponse(code = 7004, message = "비밀번호를 입력해주세요"),
            @ApiResponse(code = 7007, message = "비밀번호가 올바르지 않습니다.")})
    public ResponseTemplate<String> withdrawUser(@RequestBody WithdrawRequest withdrawRequest) {
            // 해당하는 유저의 정보를 가져왔음
        try {
            String email = userService.getAuthUserEmail();
            userServiceImpl.deleteUser(email, withdrawRequest); // 동일하다는 내용을 확인 됐다면 업데이트 진행
            String result = "회원 정보가 삭제되었습니다.";

            return new ResponseTemplate<>(result);

        } catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

}
