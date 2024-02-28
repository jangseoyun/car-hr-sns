package com.car.sns.security;

import com.car.sns.application.usecase.user.UserReadUseCase;
import com.car.sns.domain.user.model.UserAccountDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.token.secret}")
    private String secretKey;
    private final UserReadUseCase userReadUseCase;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //권한을 주거나 주지 않는다 ( 입장 ex) 티켓 확인)
        //개찰구 역할
        //현재는 모두 닫혀 있습니다
        log.info("doFilter in CustomOncePerRequestFilter");
        //문 열어주기
        //get header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            log.error("Error occurs while getting header");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

            if (JwtUtil.isExpired(token, secretKey)) {
                log.info("Token 유효 기간이 지났습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            String userId = JwtUtil.getUserName(token, secretKey);
            UserAccountDto userAccountDto = userReadUseCase.searchUser(userId).orElseThrow();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    CarAppPrincipal.from(userAccountDto), null, null
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch ( RuntimeException e) {
            filterChain.doFilter(request, response);
            log.error("Error occurs while validating : {}", e.toString());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
