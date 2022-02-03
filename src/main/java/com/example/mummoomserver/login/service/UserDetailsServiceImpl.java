
package com.example.mummoomserver.login.service;

import com.example.mummoomserver.login.users.Role;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 로그인 하면 파라미터로 전달된 username으로 계정 정보를 찾아 넘겨준다.

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByNickName(username).orElseThrow(() ->
                new UsernameNotFoundException("등록되지 않은 회원입니다."));

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .userIdx(user.getUserIdx())
                .username(user.getUsername())
                .nickName(user.getNickName())
                .imgUrl(user.getImgUrl())
                .email(user.getEmail())
                .password(user.getPassword())
                .type(user.getType())
                .role(Role.USER)
                .build();

        return userDetails;
    }
}