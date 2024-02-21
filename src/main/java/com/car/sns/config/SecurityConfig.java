package com.car.sns.config;

import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.security.CarAppPrincipal;
import com.car.sns.infrastructure.repository.UserAccountJpaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/",
                                "/articles/index"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .defaultSuccessUrl("/articles/index")
                        .permitAll()
                )
                .logout(logout -> logout
                        // 로그아웃 요청을 처리할 URL 설정
                        .logoutUrl("/logout")
                        // 로그아웃 성공 시 리다이렉트할 URL 설정
                        .logoutSuccessUrl("/articles/index")
                        // 로그아웃 핸들러 추가 (세션 무효화 처리)
                        .addLogoutHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            session.invalidate();
                        })
                        // 로그아웃 성공 핸들러 추가 (리다이렉션 처리)
                        .logoutSuccessHandler((request, response, authentication) ->
                                response.sendRedirect("/login"))
                        // 로그아웃 시 쿠키 삭제 설정 (예: "remember-me" 쿠키 삭제)
                        .deleteCookies("remember-me")
                ).build();
    }

    /*@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {//spring security에서 제외하겠다는 것
        //static resource 제외 (css, js ...)
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }*/

    @Bean
    public UserDetailsService userDetailsService(UserAccountJpaRepository userAccountRepository) {
        return username -> userAccountRepository.findByUserId(username)
                .map(UserAccountDto::from)
                .map(CarAppPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자 입니다."));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
