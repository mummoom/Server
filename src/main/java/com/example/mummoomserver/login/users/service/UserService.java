package com.example.mummoomserver.login.users.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.login.users.dto.UserDto;
import com.example.mummoomserver.login.users.requestResponse.SignUpRequest;
import com.example.mummoomserver.login.users.requestResponse.UpdateProfileRequest;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    void saveUser(SignUpRequest signUpRequest);

    String getAuthUserNickname() throws ResponeException;

    UserDto getUserProfile(String email) throws ResponeException;

    String getAuthUserEmail();

}