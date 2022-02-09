package com.example.mummoomserver.login.users.service;

import com.example.mummoomserver.login.users.dto.GoogleUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleLoginService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String GoogleRquestURL ="https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=";//youraccess_token



    public ResponseEntity<String> createRequest(String accessToken){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);


        log.info("Access token으로 구글 유저 정보 요청");
        return restTemplate.exchange(GoogleRquestURL, HttpMethod.GET,request,String.class);



    }


    public GoogleUser getUserInfo(ResponseEntity<String> userInfoResponse){

        GoogleUser googleUser = null;
        try {
            googleUser = objectMapper.readValue(userInfoResponse.getBody(), GoogleUser.class);
        } catch (JsonProcessingException e) {
            log.info("json 변환 실패");
            e.printStackTrace();
        }
        return googleUser;
    }


}
