package com.car.sns.security;

import com.car.sns.domain.user.model.UserAccountDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record CarAppPrincipal(
        //entity에서 사용하는 이름이 아닌 principal에서 사용하는 이름을 따라감
        Collection<? extends GrantedAuthority> authorities,
        String username,
        String password,
        String email,
        String nickname,
        String memo

) implements UserDetails {

    public static CarAppPrincipal of(String username, String password, String email, String nickname, String memo) {
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
                memo);
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //권한
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
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
}
