package com.car.sns.presentation.model.request;

public record RegisterAccountRequest(
        String userId,
        String password,
        String email,
        String nickname,
        String memo
) {
    public static RegisterAccountRequest of(String userId,
                                            String password,
                                            String email,
                                            String nickname,
                                            String memo) {
        return new RegisterAccountRequest( userId, password, email, nickname, memo);
    }
}
