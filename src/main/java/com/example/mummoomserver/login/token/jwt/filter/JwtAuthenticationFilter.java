package com.example.mummoomserver.login.token.jwt.filter;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus;
import com.example.mummoomserver.login.token.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.EMPTY_JWT;
import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.INVALID_JWT;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        log.info("헤더에서 토큰 받아옴 : {}", token);
        // 유효한 토큰인지 확인합니다.
        if(token == null){
            log.info("토큰 없음");

        }else if(jwtProvider.validateToken(token)){
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication = jwtProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("토큰 유효하다");
        } else{
            log.info("토큰 유효하지 않음");

        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        Collection<String> excludeUrlPatterns = new LinkedHashSet<>();
        excludeUrlPatterns.add("/api/users/login/**");
        excludeUrlPatterns.add("/api/users/signup/**");

        return excludeUrlPatterns.stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getServletPath()));
    }


}