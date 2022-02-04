//package com.example.mummoomserver.login.users.controller;
//
//import com.example.mummoomserver.login.token.oauth.OAuthToken;
//import com.example.mummoomserver.login.users.KakaoProfile;
//import com.example.mummoomserver.login.users.Role;
//import com.example.mummoomserver.login.users.User;
//import com.example.mummoomserver.login.users.service.UserService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import net.bytebuddy.dynamic.scaffold.MethodGraph;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.UUID;
//
//
//@Controller
//public class OAuthController {
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/auth/kakao/callback")
//    public @ResponseBody String kakoCallback(String code) { //data를 리턴해주는 컨트롤러 함수
//        //post방식을 key value 데이터를 요청
//
//        RestTemplate rt= new RestTemplate();
//
//        //HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //HttpBody 오브젝트 생성
//        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type","authorization_code");
//        params.add("client_id","805bbf422b921a63ed8df325ed13428b");
//        params.add("redirect_uri","http://localhost:8080/login/auth/kakao/callback");
//        params.add("code",code);
//
//        //HttpHeader와 httpbody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
//                new HttpEntity<>(params,headers);
//        //http 요청하기 post 방식으로 그리고 response의 응답을 받는다.
//        ResponseEntity<String> response = rt.exchange(
//            "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        // json 객체를 자바에서 처리하기 위해 가져온
//        // json 데이터를 오브젝트에 담는다.
//        ObjectMapper objectMapper = new ObjectMapper();
//        OAuthToken oauthToken = null;
//        try {
//        oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
//        } catch(JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e){
//            e.printStackTrace();
//        }
//        System.out.println("카카오 엑세스토큰" + oauthToken.getAccess_token());
//
//
//        //카카오 유저정보 가져오기
//        RestTemplate rt2 = new RestTemplate();
//
//        //HttpHeader 오브젝트 생성
//        HttpHeaders headers2 = new HttpHeaders();
//        headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
//        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //HttpHeader와 httpbody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 =
//                new HttpEntity<>(headers2);
//
//        //http 요청하기 post 방식으로 그리고 response의 응답을 받는다.
//        ResponseEntity<String> response2 = rt2.exchange(
//                "https://kauth.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoProfileRequest2,
//                String.class
//        );
//        System.out.println(response2.getBody());
//
//        ObjectMapper objectMapper2 = new ObjectMapper();
//        KakaoProfile kakaoProfile = null;
//        try {
//            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
//        } catch(JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e){
//            e.printStackTrace();
//        }
//        // User 오브젝트 닉네임, 이메일, 패스워드
//        System.out.println("카카오아이디(번호):"+kakaoProfile.getId());
//        System.out.println("카카오 이메일"+kakaoProfile.getKakao_account().getEmail());
//
//        System.out.println("멍박사의 밥학사전 유저네임 :"+kakaoProfile.getProperties().getNickname());
//        System.out.println("멍박사의 밥학사전 이메일 :"+kakaoProfile.getKakao_account().getEmail());
//        UUID garbagePassword =  UUID.randomUUID();
//        System.out.println("멍박사의 밥학사전 패스워드 :"+garbagePassword); //회원가입 시 비밀번호는필수이므로 랜덤하게 넣어준다.
//
//        User user = User.builder()
//            .nickName(kakaoProfile.getProperties().getNickname())
//            .password(garbagePassword.toString())
//            .email(kakaoProfile.getKakao_account().getEmail())
//            .build();
//
//
//        return response2.getBody();
//    }
//
//}
