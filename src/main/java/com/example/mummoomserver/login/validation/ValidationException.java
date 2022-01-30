package com.example.mummoomserver.login.validation;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;


// 유효성 검사 실패 시 클라이언트에서 해당 정보를 활용할 수 있도록 에러 정보를 담는 커스텀 exception 클래스

public class ValidationException extends RuntimeException{

    private List<FieldError> errors = new ArrayList<>();

    public ValidationException() {
    }

    public ValidationException(String message, FieldError fieldError) {
        super(message);
        this.errors.add(fieldError);
    }

    public ValidationException(String message, Throwable cause, FieldError fieldError) {
        super(message, cause);
        this.errors.add(fieldError);
    }

    public ValidationException(String message, List<FieldError> errors) {
        super(message);
        this.errors = errors;
    }

    public ValidationException(String message, Throwable cause, List<FieldError> errors) {
        super(message, cause);
        this.errors = errors;
    }

    public List<FieldError> getErrors(){
        return errors;
    }
}
