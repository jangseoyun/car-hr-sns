package com.car.sns.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppErrorCode {
    EncryptPasswordIsBlank				( "500001",	HttpStatus.NOT_FOUND, "Encrypt password is blank"),

    //사용자 계정
    USER_ALREADY_EXIST_ACCOUNT          ("UA0001", HttpStatus.CONFLICT, "이미 가입된 사용자 입니다"),
    INVALID_USER_ID_PASSWORD            ("UA0002", HttpStatus.FORBIDDEN, "아이디 또는 비밀번호가 일치하지 않습니다. 회원정보를 확인해주세요");

    private String errorCode;
    private HttpStatus status;
    private String message;
}
