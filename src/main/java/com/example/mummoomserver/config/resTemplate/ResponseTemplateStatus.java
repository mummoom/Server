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
    INVALID_PARAM(false,"잘못된 PathVariable Parameter입니다,",4000),
    NO_SEARCH_RESULT(false,"해당 재료의 검색 결과가 없습니다",4001),


    /**
     * 6000 : 강아지 정보 요청 관련 오류
     */
    EMPTY_DOG_NAME(false, "강아지 이름을 입력해주세요.", 6000),
    EMPTY_DOG_BIRTH(false, "강아지 성별을 입력해주세요.", 6001),
    EMPTY_DOG_TYPE(false, "강아지 종을 입력해주세요.", 6002),
    EMPTY_DOG_SEX(false, "강아지 성별을 입력해주세요.", 6003),
    EMPTY_DOG_SURGERY(false, "강아지 중성화 수술 정보를 입력해주세요.", 6004),
    INVALID_DOG_INDEX(false, "존재하지 않는 강아지 Index입니다.", 6005),

    INVALID_DOG_USER(false, "유효하지 않은 유저입니다.", 6006),
    NOT_EXIST_USER(false, "회원 정보를 찾을 수 없습니다.", 6007),

    /**
     * 7000 : 유저 정보 요청 관련 오류
     */

    EMPTY_UPDATE_PASSWORD(false, "변경할 비밀번호를 입력해주세요", 7001),
    EMPTY_UPDATE(false, "변경할 닉네임이나 이미지를 입력해주세요", 7002),
    EMPTY_EMAIL(false, "이메일을 입력해주세요", 7003),
    EMPTY_PASSWORD(false, "비밀번호를 입력해주세요", 7004),
    EMPTY_NICKNAME(false, "닉네임을 입력해주세요", 7005),


    /**
     * 8000 : 커뮤니티 요청 관련 오류
     */
    INVALID_POST_IDX(false, "존재하지 않는 게시글 입니다.", 8000),
    INVALID_USER(false, "회원정보를 찾을 수 없습니다.", 8001),
    INVALID_COMMENT_IDX(false, "존재하지 않는 댓글 입니다.", 8002),
    INVALID_NESTED_IDX(false, "존재하지 않는 대댓글 입니다.", 8003),
    PERMISSION_DENIED(false, "작성자만 사용할 수 있습니다.", 8004),
    EMPTY_TITLE(false, "제목을 입력해주세요.", 8005),
    EMPTY_CONTENT(false, "내용을 입력해주세요.", 8006);




    private final boolean isSuccess;
    private final int code;
    private final String message;

    private ResponseTemplateStatus(boolean isSuccess, String message, int code) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }


}
