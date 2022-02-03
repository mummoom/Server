package com.example.mummoomserver.login.users;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus;
import com.example.mummoomserver.login.service.SessionUser;
import com.example.mummoomserver.login.users.requestResponse.SignUpRequest;
import com.example.mummoomserver.login.users.requestResponse.UpdateProfileRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public interface UserService {

    void saveUser(SignUpRequest signUpRequest);

    void updateProfile(String username, UpdateProfileRequest updateProfileRequest);

}