package com.car.sns.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppErrorCode {
    EncryptPasswordIsBlank				( "500001",	HttpStatus.NOT_FOUND, "Encrypt password is blank");

    private String errorCode;
    private HttpStatus status;
    private String message;
}
