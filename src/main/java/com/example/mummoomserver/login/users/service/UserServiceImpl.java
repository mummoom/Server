package com.example.mummoomserver.login.users.service;

import com.example.mummoomserver.login.service.UserDetailsImpl;
import com.example.mummoomserver.login.users.*;
import com.example.mummoomserver.login.users.requestResponse.SignUpRequest;
import com.example.mummoomserver.login.users.requestResponse.UpdateProfileRequest;
import com.example.mummoomserver.login.validation.SimpleFieldError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    /**
     *
     * 현석
     * 시큐리티객체로부터 유저정보 뽑아옴
     */

    private final UserRepository userRepository;
//    private final OAuth2AccountRepository oAuth2AccountRepository;
    private final PasswordEncoder passwordEncoder;




    @Override
    public void saveUser(SignUpRequest signUpRequest){
        checkDuplicateEmail(signUpRequest.getEmail());
        User user = User.builder()
                .nickName(signUpRequest.getNickname())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .type(UserType.DEFAULT)
                .username(signUpRequest.getEmail())
                .role(Role.GUEST)
                .build();

        userRepository.save(user);
    }


    @Override
    public void updateProfile(String username, UpdateProfileRequest updateProfileRequest){

        User user = userRepository.findByNickName(username).get();

        //이름이 변경되었는지 체크
        if (!user.getNickName().equals(updateProfileRequest.getNickName()))
            user.updateName(updateProfileRequest.getNickName());

        //이메일이 변경되었는지 체크
        if (!user.getEmail().equals(updateProfileRequest.getEmail())) {
            checkDuplicateEmail(updateProfileRequest.getEmail());
            user.updateEmail(updateProfileRequest.getEmail());
        }
    }

    private void checkDuplicateEmail(String email) {
        if(userRepository.existsByEmail(email))
            throw new DuplicateUserException("사용중인 이메일 입니다.", new SimpleFieldError("email", "사용중인 이메일 입니다."));
    }

    private User checkRegisteredUser(String username) {
        Optional<User> optUser = userRepository.findByNickName(username);
        Assert.state(optUser.isPresent(), "가입되지 않은 회원입니다.");
        return optUser.get();
    }


    // 현석- 추가됨
    @Override
    public String getAuthUserNickname() {

        UserDetailsImpl userDetails= (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails instanceof UserDetails){ //인증된 유저여야함
            return userDetails.getNickName();
        }else{

            log.info("인증되지 않은 유저의 정보이므로 유저 닉네임을 불러올 수 없습니다.");
            return userDetails.toString();
        }

    }

    @Override
    public String getAuthUserEmail() {
        UserDetailsImpl userDetails= (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails instanceof UserDetails) { //인증된 유저여야함
            return userDetails.getEmail();
        }else{
            log.info("인증되지 않은 유저의 정보이므로 유저 이메일을 불러올 수 없습니다.");
            return userDetails.toString();
        }

    }
}