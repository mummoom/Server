package com.example.mummoomserver.login.users.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus;
import com.example.mummoomserver.login.token.jwt.JwtProvider;


import com.example.mummoomserver.login.service.UserDetailsImpl;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import com.example.mummoomserver.login.users.dto.UserDto;
import com.example.mummoomserver.login.users.requestResponse.SignUpRequest;
import com.example.mummoomserver.login.users.requestResponse.UpdateProfileRequest;
import com.example.mummoomserver.login.users.requestResponse.UserProfileResponse;
import com.example.mummoomserver.login.users.service.UserService;
import com.example.mummoomserver.login.users.requestResponse.LoginRequest;
import com.example.mummoomserver.login.users.service.UserServiceImpl;
import com.example.mummoomserver.login.validation.ValidationException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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

    // 회원가입
    @ApiOperation(value = "회원가입 API", notes = "회원정보(이메일,닉네임,비밀번호)를 모두 필수로 받습니다.")
    @PostMapping("/signup")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "nickName", value = "1~20자를 입력받으며 중복이 되지 않습니다."),
    @ApiImplicitParam(name = "paassword", value = "6~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다.")})
    public ResponseTemplate<?> signUpNewUser(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult) {
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
                                                "(현재상태는) 성공 시 response 헤더에 X-AUTH-TOKEN 이라는 이름으로 토큰 정보가 반환됩니다.")
    @PostMapping("/login")
    public ResponseTemplate<String> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws IOException {
        //실패시
        User member = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        //비밀번호 틀릴 시
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        // 로그인 성공 시  // 유저 이메일을 삽입
        String token = jwtProvider.createToken(member.getEmail(), member.getRole());

        jwtProvider.writeTokenResponse(httpServletResponse, token);
        return new ResponseTemplate("로그인 성공");
    }

    /**
     * - 현석
     * 현재 아래 코드들은 기능상 중복되거나 유효하지 않아서 주석처리함
     */

    //프로필 자신 조회
    @ApiOperation(value = "내정보 조회 API", notes = "이메일, 비밀번호,닉네임, 프로필 이미지를 조회할 수 있습니다.")
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
    @ApiOperation(value = "내정보 수정 API", notes = "이메일, 비밀번호,닉네임 변경이 가능하고 프로필 이미지를 여기에서 등록할 수 있습니다.")
    @PutMapping("/me")
    public ResponseTemplate<String> updateProfile(@RequestBody  UpdateProfileRequest updateProfileRequest) {
        //입력해준 값이 없을 때의 예외 처리
//        if (UserDto.getNickName() == null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_NICKNAME);
//        if (UserDto.getPassword() == null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_PASSWORD);
        try {
            String email = userService.getAuthUserEmail();
            userServiceImpl.updateProfile(email, updateProfileRequest);

            return new ResponseTemplate<>("회원 정보 수정에 성공했습니다.");
        } catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

//     //회원 탈퇴ㅣ?
//    @DeleteMapping("/withdraw")
//    public void withdrawUser(@AuthenticationPrincipal UserDetailsImpl loginUser, HttpServletRequest request, HttpServletResponse response) {
//        Optional<OAuth2AccountDTO> optionalOAuth2AccountDTO = userService.withdrawUser(loginUser.getUsername());
//        //연동된 소셜계정 정보가 있다면 연동해제 요청
//        if(optionalOAuth2AccountDTO.isPresent()) {
//            OAuth2AccountDTO oAuth2AccountDTO = optionalOAuth2AccountDTO.get();
//            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(oAuth2AccountDTO.getProvider());
//            OAuth2Service oAuth2Service = OAuth2ServiceFactory.getOAuth2Service(restTemplate, oAuth2AccountDTO.getProvider());
//            oAuth2Service.unlink(clientRegistration, oAuth2AccountDTO.getOAuth2Token());
//        }
// }
}
