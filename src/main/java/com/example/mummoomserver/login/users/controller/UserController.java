package com.example.mummoomserver.login.users.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus;
import com.example.mummoomserver.domain.Dog.entity.Dog;
import com.example.mummoomserver.domain.Dog.repository.DogRepository;
import com.example.mummoomserver.login.token.jwt.JwtProvider;


import com.example.mummoomserver.login.users.Role;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import com.example.mummoomserver.login.users.dto.GoogleUser;
import com.example.mummoomserver.login.users.dto.UserDto;
import com.example.mummoomserver.login.users.requestResponse.SignUpRequest;
import com.example.mummoomserver.login.users.service.GoogleLoginService;
import com.example.mummoomserver.login.users.dto.LoginDto;

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

import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.lang.reflect.Member;
import java.util.Optional;

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
    private final GoogleLoginService googleLoginService;

    private final DogRepository dogRepository;


    // 회원가입
    @ApiOperation(value = "회원가입 API", notes = "회원정보(이메일,닉네임,비밀번호)를 모두 필수로 받습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스 에러"),
            @ApiResponse(code = 7003, message = "이메일을 입력해주세요."),
            @ApiResponse(code = 7004, message = "비밀번호를 입력해주세요."),
            @ApiResponse(code = 7005, message = "닉네임을 입력해주세요"),
            @ApiResponse(code = 7007, message = "비밀번호가 올바르지 않습니다."),
            @ApiResponse(code=7010, message ="회원가입 양식을 다시한 번 확인해주세요"),
            @ApiResponse(code=7011, message ="이미 존재하는 닉네임입니다"),
            @ApiResponse(code=7012, message ="이미 존재하는 이메일입니다")    })

    @PostMapping("/signup")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "1~20자를 입력받으며 중복이 되지 않습니다."),
            @ApiImplicitParam(name = "paassword", value = "6~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다.")})

    public ResponseTemplate<?> signUpNewUser(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult) {
        if(signUpRequest.getEmail()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_EMAIL);
        if(signUpRequest.getNickName()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_NICKNAME);
        if(signUpRequest.getPassword()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_PASSWORD);
        //유효성 검사
        if (bindingResult.hasErrors()) return new ResponseTemplate<>(ResponseTemplateStatus.INVALID_SIGNUP);
        //성공 시 이메일 및 닉네임 중복 체크
        String email = signUpRequest.getEmail();
        String nickName = signUpRequest.getNickName();
        if(userRepository.existsByEmail(email)==true) {
            return new ResponseTemplate<>(ResponseTemplateStatus.EMAIL_DUPLICATED);
        }
        else if(userRepository.existsByNickName(nickName)==true){
            return new ResponseTemplate<>(ResponseTemplateStatus.NICKNAME_DUPLICATED);
        }
        else{
            userService.saveUser(signUpRequest);
            return new ResponseTemplate("회원가입 성공");

        }

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
    public ResponseTemplate<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws IOException {
        if(loginRequest.getEmail()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_EMAIL);
        if(loginRequest.getPassword()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_PASSWORD);

        if(userRepository.existsByEmail(loginRequest.getEmail())==false)
            return new ResponseTemplate<>(ResponseTemplateStatus.INVALID_EMAIL);
        User member = userRepository.findByEmail(loginRequest.getEmail()).get();
        //비밀번호 틀릴 시
        if(passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())==false)
            return new ResponseTemplate<>(ResponseTemplateStatus.INVALID_PASSWORD);
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
     * 구글 소셜 로그인
     * 테스트필요
     * @return 성공시 jwt 토큰과 강아지 정보 여부 반환
     */
    @GetMapping("/login/google")
    @ApiOperation(value = "구글 로그인 API", notes = "구글 Access token을 전달하여 멈뭄에 로그인합니다")
    @ApiImplicitParam(name = "accessToken", value = "구글 Access token")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스 에러"),
            @ApiResponse(code= 7006, message = "토큰이 유효하지 않음")
    })

    public ResponseTemplate<LoginDto> googleLogin(@RequestParam(name="accessToken") String accessToken){
        boolean dog_exist;
        try {
            ResponseEntity<String> userInfoResponse = googleLoginService.createRequest(accessToken);
            GoogleUser googleUser = googleLoginService.getUserInfo(userInfoResponse);

            log.info("로그인한 구글 유저 정보 \n {} ", googleUser);
            LoginDto loginDto = null;
            Optional<User> member = userRepository.findByEmail(googleUser.getEmail());

            if (member.isPresent()) {
                log.info("DB에 유저 정보 있음");
                dog_exist = dogRepository.existsByUser_userIdx(member.get().getUserIdx());
                String token = jwtProvider.createToken(member.get().getEmail(), Role.USER);
                loginDto = new LoginDto(token, dog_exist); //토큰과 강아지 정보 여부 반환

            } else {
                //DB에 정보가 없다면 DB에 유저정보 저장
                log.info("DB에 유저 정보 없음");
                UserDto user = googleUser.toUserDto(googleUser.getEmail(),
                        googleUser.getName(),
                        googleUser.getPicture());
                userService.saveOAuthUser(user);
                dog_exist = false;
                String token = jwtProvider.createToken(user.getEmail(), Role.USER);
                loginDto = new LoginDto(token, dog_exist); //토큰과 강아지 정보 여부 반환

            }

            return new ResponseTemplate<>(loginDto);
            //유저인덱스와 연결된 강아지 정보가 있다면 return true, 아니면 false
        }catch (Exception e){
            return new ResponseTemplate<>(ResponseTemplateStatus.INVALID_OAUTH_ACCESS_TOKEN);
        }

    }


    /**
     * 카카오 소셜 로그인
     * @return 성공시 jwt 토큰과 강아지 정보 여부 반환
     */
//
//    @GetMapping("/login/kakao")
//    public ResponseTemplate<LoginDto> kakaoLogin(@RequestParam(name="accessToken") String accessToken){
//        boolean dog_exist;
//
//    }

    /**
     * - 현석
     * 현재 아래 코드들은 기능상 중복되거나 유효하지 않아서 주석처리함
     */


//    @ApiOperation(value = "로그아웃 API")
//    @GetMapping("/user/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        ResponseTemplate<>("로그아웃이 완료되었습니다");
//        return "redirect:/";
//    }


    //프로필 자신 조회
    @ApiOperation(value = "내정보 조회 API", notes = "이메일, 닉네임, 프로필 이미지를 조회할 수 있습니다. jwt 토큰을 입력해주어야 합니다.")
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
    @ApiOperation(value = "내 프로필 수정 API", notes = "프로필 이미지를 수정할 수 있습니다. jwt토큰을 입력해주어야 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스에러"),
            @ApiResponse(code = 7002, message = "변경할 사항이 없습니다.")})
    @PatchMapping("/myimg")
    public ResponseTemplate<String> updateImgUrl(@RequestBody UpdateImgRequest updateImgRequest) {
        //입력해준 값이 없을 때의 예외 처리
        String email = userService.getAuthUserEmail();
        User user = userRepository.findByEmail(email).get();
        if (updateImgRequest.getImgUrl() == null) //변경하는 이미지가 없는 경우
            return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_UPDATE);
        user.updateImgUrl(updateImgRequest.getImgUrl());
        userRepository.save(user);
        String result = "프로필 이미지 수정에 성공했습니다.";
        return new ResponseTemplate<>(result);
    }

    // 프로필 수정 사항 반영
    @ApiOperation(value = "내 닉네임 수정 API", notes = "닉네임 변경이 가능합니다. jwt토큰을 입력해주어야 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스에러"),
            @ApiResponse(code = 7002, message = "변경할 사항이 없습니다"),
            @ApiResponse(code = 7011, message = "이미 존재하는 닉네임입니다."),
            @ApiResponse(code = 7016, message = "닉네임 형식을 다시한 번 확인해주세요")})
    @PatchMapping("/mynickname")
    public ResponseTemplate<String> updateNickName(@RequestBody @Valid  UpdateNickNameRequest updateNickNameRequest, BindingResult bindingResult) {
        //입력해준 값이 없을 때의 예외 처리
        String email = userService.getAuthUserEmail();
        User user = userRepository.findByEmail(email).get();
        if (updateNickNameRequest.getNickName() == null) //변경하는 닉네임이 없는 경우
            return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_UPDATE);
        if (user.getNickName() == updateNickNameRequest.getNickName()) // 변경하는 닉네임이 동일한 경우
            return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_UPDATE);
        if (userRepository.existsByNickName(updateNickNameRequest.getNickName())==true){
            return new ResponseTemplate<>(ResponseTemplateStatus.NICKNAME_DUPLICATED);}
        //유효성 검사
        if (bindingResult.hasErrors()) return new ResponseTemplate<>(ResponseTemplateStatus.INCORRECT_NICKNAME);

        user.updateNickName(updateNickNameRequest.getNickName());
        userRepository.save(user);
        String result = "닉네임 수정에 성공했습니다.";
        return new ResponseTemplate<>(result);
    }

    // 비밀번호 수정
    @ApiOperation(value = "비밀번호 수정 API", notes = "비밀번호를 확인한 후에 변경이 가능합니다. jwt토큰을 입력해주어야 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스에러"),
            @ApiResponse(code = 7001, message = "변경할 비밀번호를 입력해주세요"),
            @ApiResponse(code = 7004, message = "비밀번호를 입력해주세요."),
            @ApiResponse(code = 7007, message = "비밀번호가 올바르지 않습니다."),
            @ApiResponse(code = 7009, message = "비밀번호 형식을 다시 한번 확인해주세요.")})
    @PutMapping("/pwd")
    public ResponseTemplate<String> updatePwd(@RequestBody @Valid UpdatePwdRequest updatePwdRequest, BindingResult bindingResult) {
        //입력해준 값이 없을 때의 예외 처리
        if (updatePwdRequest.getLastPassword() == null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_PASSWORD);
        if (updatePwdRequest.getNewPassword() == null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_UPDATE_PASSWORD);
        try {
            String email = userService.getAuthUserEmail();
            User member = userRepository.findByEmail(email).get();
            // 해당하는 유저의 정보를 가져왔음
            if(passwordEncoder.matches(updatePwdRequest.getLastPassword(), member.getPassword())==false)
                return new ResponseTemplate<>(ResponseTemplateStatus.INVALID_PASSWORD);
            //유효성 검사
            if (bindingResult.hasErrors()) return new ResponseTemplate<>(ResponseTemplateStatus.INCORRECT_PASSWORD);

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
            @ApiResponse(code = 7007, message = "비밀번호가 올바르지 않습니다."),
            @ApiResponse(code = 7014, message = "탈퇴 사유를 입력해주세요.")})
    public ResponseTemplate<String> withdrawUser(@RequestBody WithdrawRequest withdrawRequest) {
        if (withdrawRequest.getWithdrawPassword() == null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_PASSWORD);
        if (withdrawRequest.getWithdrawReason() == null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_WITHDRAWREASON);
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
