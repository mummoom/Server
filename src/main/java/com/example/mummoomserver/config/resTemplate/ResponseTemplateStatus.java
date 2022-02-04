package com.example.mummoomserver.config.resTemplate;

import lombok.Getter;

@Getter
public enum ResponseTemplateStatus {
    /**    private final Boolean isSuccess;
     private final String message;
     private final int code; //내부 코드
     private T data;

     *
     * 1000: 요청 성공
     */
    SUCCESS(true,"요청 성공", 1000),
    FAIL(false,"요청 실패",1004),


    /**
     *
     * 2000 : Request 오류

     */
    EMPTY_JWT(false,"JWT를 입력해주세요.",2001),
    INVALID_JWT(false, "유효하지 않은 JWT입니다.",2002),



    /**
     *
     * DB 부분 오류
     *
     */
    DATABASE_ERROR(false, "데이터베이스 요청 에러.",3000),

    /**
     * 4000 : 식재료 요청 관련 오류
     */
    INVALID_PARAM(false,"잘못된 PathVariable Parameter입니다,",4000);








    private final boolean isSuccess;
    private final int code;
    private final String message;

    private ResponseTemplateStatus(boolean isSuccess, String message, int code) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }


}
