package com.example.mummoomserver.login.users;

import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.login.security.UserDetailsImpl;

import com.example.mummoomserver.login.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RestTemplate restTemplate;
    private final ClientRegistrationRepository clientRegistrationRepository;

    // 회원가입
    @PostMapping("/signup")
    public ResponseTemplate<?> signUpNewUser(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new ValidationException("회원가입 유효성 검사 실패.", bindingResult.getFieldErrors());
        // 성공시
        userService.saveUser(signUpRequest);
        return new ResponseTemplate("회원가입 성공");
    }

    //프로필 자신 조회 //권한 문제로 물려있음
    @GetMapping("/me")
    public ResponseTemplate<?> getUserProfile(@AuthenticationPrincipal UserDetailsImpl loginUser) {
        UserProfileResponse.UserProfileResponseBuilder userProfileResponseBuilder = UserProfileResponse.builder()
                .userIdx(loginUser.getUserIdx())
                .nickName(loginUser.getNickName())
                .email(loginUser.getEmail())
                .role(loginUser.getRole().stream().map(GrantedAuthority::getAuthority).map(s -> AuthorityType.valueOf(s)).collect(Collectors.toList()));

        //연동된 소셜 계정이 존재하면 소셜 계정 정보 추가
        Optional<OAuth2AccountDTO> optionalOAuth2AccountDTO = userService.getOAuth2Account(loginUser.getUsername());
        if(optionalOAuth2AccountDTO.isPresent()) {
            OAuth2AccountDTO oAuth2AccountDTO = optionalOAuth2AccountDTO.get();
            userProfileResponseBuilder.socialProvider(oAuth2AccountDTO.getProvider()).linkedAt(oAuth2AccountDTO.getCreatedAt());
        }
        return new ResponseTemplate(userProfileResponseBuilder.build());
    }

   // 프로필 수정 사항 반영
    @PutMapping("/me")
    public void updateProfile(@RequestBody @Valid UpdateProfileRequest updateProfileRequest, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl loginUser) {
        if(bindingResult.hasErrors()) throw new ValidationException("프로필 업데이트 유효성 검사 실패", bindingResult.getFieldErrors());
        userService.updateProfile(loginUser.getUsername(), updateProfileRequest);

    }

    // 회원 탈퇴ㅣ?
    @DeleteMapping("/withdraw")
    public void withdrawUser(@AuthenticationPrincipal UserDetailsImpl loginUser, HttpServletRequest request, HttpServletResponse response) {
//        Optional<OAuth2AccountDTO> optionalOAuth2AccountDTO = userService.withdrawUser(loginUser.getUsername());
//        //연동된 소셜계정 정보가 있다면 연동해제 요청
//        if(optionalOAuth2AccountDTO.isPresent()) {
//            OAuth2AccountDTO oAuth2AccountDTO = optionalOAuth2AccountDTO.get();
//            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(oAuth2AccountDTO.getProvider());
//            OAuth2Service oAuth2Service = OAuth2ServiceFactory.getOAuth2Service(restTemplate, oAuth2AccountDTO.getProvider());
//            oAuth2Service.unlink(clientRegistration, oAuth2AccountDTO.getOAuth2Token());
//        }
    }
}
