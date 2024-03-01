package com.car.sns.presentation.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String status;
    private T result;

    public static <T> Result<T> error(T result) {
        return new Result("ERROR", result);
    }

    public static <T> Result<T> success(T result) {
        return new Result("SUCCESS", result);
    }
}
