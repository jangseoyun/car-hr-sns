package com.car.sns.config;

import com.car.sns.presentation.model.response.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.car.sns.exception.model.AppErrorCode.INVALID_TOKEN;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(INVALID_TOKEN.getStatus().value());
        response.getWriter().write(Result.error(INVALID_TOKEN.getMessage()).getResult());
    }
}
