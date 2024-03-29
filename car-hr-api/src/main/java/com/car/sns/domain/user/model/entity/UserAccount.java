package com.car.sns.domain.user.model.entity;

import com.car.sns.common.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@Table(name = "t_user_account",
        indexes = {
                @Index(columnList = "userId", unique = true),
                @Index(columnList = "email", unique = true),
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")
        })
public class UserAccount extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private Long userAccountId;

    @Column(name = "user_id"
            , length = 50)
    private String userId;

    @Column(name = "user_password"
            , nullable = false)
    private String userPassword;

    @Column(name = "email"
            , length = 100
            , nullable = false)
    private String email;

    @Column(name = "nickname"
            , length = 100
            , nullable = false)
    private String nickname;

    @Column(name = "memo")
    private String memo;

    @Column(name = "kakao_createdBy")
    private String oAuthCreatedBy;

    private UserAccount(String userId, String userPassword, String email, String nickname, String memo, String oAuthCreatedBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.oAuthCreatedBy = oAuthCreatedBy;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo, String oAuthCreatedBy) {
        return new UserAccount(userId, userPassword, email, nickname, memo, oAuthCreatedBy);
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return UserAccount.of(userId, userPassword, email, nickname, memo, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getUserAccountId() != null && this.getUserAccountId().equals(that.getUserAccountId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserAccountId());
    }
}
