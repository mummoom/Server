package com.example.mummoomserver.login.users.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.requestResponse.SignUpRequest;
import com.example.mummoomserver.login.users.requestResponse.UpdateProfileRequest;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    void saveUser(SignUpRequest signUpRequest);

    void updateProfile(String username, UpdateProfileRequest updateProfileRequest);

    String getAuthUserNickname();

    String getAuthUserEmail() throws ResponeException;






}