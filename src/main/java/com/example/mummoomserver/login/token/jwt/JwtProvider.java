package com.example.mummoomserver.login.token.jwt;


import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.login.users.Role;
import com.example.mummoomserver.login.util.DateConvertor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.*;

// 토큰의 유효성을 검증하는 클래스

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {
    private String secretKey = "llshlllshlllshlllshl";

    // 토큰 유효시간 3일
    private long tokenValidTime = 72 * 60 * 60 * 1000L;
    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String email, Role role) {
        log.info("jwt token 생성 email {}",email);
        Claims claims = Jwts.claims().setSubject(email); // JWT payload 에 저장되는 정보단위
        claims.put("role", role.getKey()); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }



    public void writeTokenResponse(HttpServletResponse response, String token) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("X-AUTH-TOKEN",token);
        response.setContentType("application/json;charset=UTF-8");

    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserName() throws ResponeException {
        String token =
                ((ServletRequestAttributes)
                        RequestContextHolder.currentRequestAttributes()).getRequest().getHeader("X-AUTH-TOKEN");

        if(token == null || token.length() == 0){
            throw new ResponeException(EMPTY_JWT);
        }

        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
        } catch (Exception ignored) {
            throw new ResponeException(INVALID_JWT);
        }

        return claims.getBody().get("sub",String.class);
    }
}