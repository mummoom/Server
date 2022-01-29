package com.example.mummoomserver.springsecurityjwt.authentication;

public class AuthenticationFailedException extends RuntimeException{

    public AuthenticationFailedException(String message) {
        super(message);
    }

    public AuthenticationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
