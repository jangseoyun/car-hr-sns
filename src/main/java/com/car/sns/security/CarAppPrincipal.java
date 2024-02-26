package com.car.sns.security;

import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.exception.CarHrSnsAppException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.car.sns.exception.model.AppErrorCode.AUTHENTICATION_TOKEN_EXIST;

public record CarAppPrincipal(
        //entity에서 사용하는 이름이 아닌 principal에서 사용하는 이름을 따라감
        Collection<? extends GrantedAuthority> authorities,
        String username,
        String password,
        String email,
        String nickname,
        String memo,
        Map<String, Object> oAuth2Attribute

) implements UserDetails, OAuth2User {

    public static CarAppPrincipal of(String username, String password, String email, String nickname, String memo, Map<String, Object> oAuth2Attribute) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new CarAppPrincipal(
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                username,
                password,
                email,
                nickname,
                memo,
                oAuth2Attribute);
    }

    public static CarAppPrincipal of(String username, String password, String email, String nickname, String memo) {
        return CarAppPrincipal.of(username, password, email, nickname, memo, Map.of());
    }

    public static CarAppPrincipal from(UserAccountDto userAccountDto) {
        return CarAppPrincipal.of(
                userAccountDto.userId(),
                userAccountDto.userPassword(),
                userAccountDto.email(),
                userAccountDto.nickname(),
                userAccountDto.memo());
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(username, password, email, nickname, memo);
    }

    /**
     * OAuth2 method
     */

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Spring security method
     */
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        if (username == null) {
            throw new CarHrSnsAppException(AUTHENTICATION_TOKEN_EXIST, AUTHENTICATION_TOKEN_EXIST.getMessage());
        }
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return username;
    }
}
