package com.car.sns.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppErrorCode {
    EncryptPasswordIsBlank			( "500001",	HttpStatus.NOT_FOUND, "Encrypt password is blank"),

    //사용자 계정
    USER_ALREADY_EXIST_ACCOUNT      ("UA0001", HttpStatus.CONFLICT, "이미 가입된 사용자 입니다"),
    USER_NOTFOUND_ACCOUNT           ("UA0002", HttpStatus.NOT_FOUND, "존재하지 않는 사용자 입니다"),
    INVALID_USER_ID_PASSWORD        ("UA0003", HttpStatus.FORBIDDEN, "아이디 또는 비밀번호가 일치하지 않습니다. 회원정보를 확인해주세요"),

    //토큰 만료
    AUTHENTICATION_TOKEN_EXIST      ("A0001", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다"),
    INVALID_TOKEN                   ("A0002", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),

    //비즈니스
    USER_NOT_MATCH                  ("SV0001", HttpStatus.FORBIDDEN, "작성자와 요청자가 일치하지 않습니다"),
    ENTITY_NOT_FOUND                ("SV1001", HttpStatus.NOT_FOUND, "존재하지 않는 게시글 입니다"),

    //DB
    DATABASE_INSERT_FAIL            ("DB0001", HttpStatus.EXPECTATION_FAILED, "데이터베이스 등록 실패"),

    //서버
    INTERNAL_SERVER_ERROR           ("SER001", HttpStatus.INTERNAL_SERVER_ERROR, "internal server error");

    private String errorCode;
    private HttpStatus status;
    private String message;
}
