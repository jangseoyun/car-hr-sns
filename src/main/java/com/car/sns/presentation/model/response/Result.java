package com.car.sns.presentation.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String status;
    private T data;

    public static <T> Result<T> error(T data) {
        return new Result("ERROR", data);
    }

    public static <T> Result<T> success(T data) {
        return new Result("SUCCESS", data);
    }
}
