package com.car.sns.domain.user.model;

import com.car.sns.domain.user.entity.UserAccount;
import com.car.sns.security.CarAppPrincipal;
import com.car.sns.security.KakaoOAuth2Response;

import java.time.LocalDateTime;

/**
 * DTO for {@link UserAccount}
 */
public record UserAccountDto(
        LocalDateTime createdAt,
        String createdBy,
        String userId,
        String userPassword,
        String email,
        String nickname,
        String memo
) {

    public static UserAccountDto of(LocalDateTime createdAt,
                                    String createdBy,
                                    String userId,
                                    String userPassword,
                                    String email,
                                    String nickname,
                                    String memo) {
        return new UserAccountDto(createdAt, createdBy, userId, userPassword, email, nickname, memo);
    }

    public static UserAccountDto of(String userId,
                                    String userPassword,
                                    String email,
                                    String nickname,
                                    String memo) {
        return new UserAccountDto(null, null, userId, userPassword, email, nickname, memo);
    }

    public static UserAccountDto from(UserAccount userAccount) {
        return new UserAccountDto(
                userAccount.getCreatedAt(),
                userAccount.getCreatedBy(),
                userAccount.getUserId(),
                userAccount.getUserPassword(),
                userAccount.getEmail(),
                userAccount.getNickname(),
                userAccount.getMemo());
    }

    public UserAccount toEntity() {
        return UserAccount.of(userId, userPassword, email, nickname, memo);
    }
}