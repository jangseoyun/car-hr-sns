package com.car.sns.exception;

import com.car.sns.exception.model.AppErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarHrSnsAppException extends RuntimeException {
    private AppErrorCode appErrorCode;
    private String message;
}
