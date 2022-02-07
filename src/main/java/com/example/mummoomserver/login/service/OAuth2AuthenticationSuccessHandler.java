//package com.example.mummoomserver.login.service;
//
//import com.example.mummoomserver.login.token.jwt.JwtProvider;
//import com.example.mummoomserver.login.users.Role;
//import com.example.mummoomserver.login.users.User;
//import com.example.mummoomserver.login.users.UserRepository;
//import com.example.mummoomserver.login.users.requestResponse.LoginRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.security.web.savedrequest.SavedRequest;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final JwtProvider jwtProvider;
//    private final UserRepository userRepository;
//    private final OAuth2Service oAuth2Service;
//
//    private RequestCache requestCache = new HttpSessionRequestCache();
//    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
//
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
//        Map<String,Object> attributes =oAuth2User.getAttributes();
//
//        // 토큰 생성
//        String token = jwtProvider.createToken((String) attributes.get("email"),Role.GUEST);
//
//        log.info("Oatuh 로그인후 토큰 생성  : {}",token);
//
//        jwtProvider.writeTokenResponse(response, token);
//
//        resultRedirectStrategy(request, response, authentication);
//    }
//
//    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
//                                          Authentication authentication) throws IOException, ServletException {
//
//        SavedRequest savedRequest = requestCache.getRequest(request, response);
//
//        if(savedRequest!=null) {
//            String targetUrl = savedRequest.getRedirectUrl();
//            redirectStratgy.sendRedirect(request, response, targetUrl);
//        } else {
//
//            String redirectUrl = request.getScheme() + "://" + request.getServerName() + ":"+8080+ "/"; //포트번호..메인 페이지로 돌아갈 수 있는 경로를 지정해줘야 한다.
//            redirectStratgy.sendRedirect(request, response, redirectUrl);
//        }
//
//    }
//
//}