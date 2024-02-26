package com.car.sns.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //권한을 주거나 주지 않는다 ( 입장 ex) 티켓 확인)
        //개찰구 역할
        //현재는 모두 닫혀 있습니다
        log.info("doFilter in CustomOncePerRequestFilter");
        //문 열어주기
        /*UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);*/
        filterChain.doFilter(request, response);
    }
}
