package com.car.sns.config;

import com.car.sns.application.usecase.user.UserReadUseCase;
import com.car.sns.domain.user.service.UserAccountReadService;
import com.car.sns.domain.user.service.UserAccountWriteService;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.security.CarAppPrincipal;
import com.car.sns.security.JwtTokenFilter;
import com.car.sns.security.KakaoOAuth2Response;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.car.sns.exception.model.AppErrorCode.USER_NOTFOUND_ACCOUNT;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.token.secret}")
    private String secretKey;
    private final PasswordEncoder passwordEncoder;
    private final UserReadUseCase userReadUseCase;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   OAuth2UserService<OAuth2UserRequest,
                                                           OAuth2User> oAuth2UserService) throws Exception {
        return httpSecurity
                .csrf((csrf) -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .anonymous(anonymous -> anonymous.disable())
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/",
                                "/articles/index"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register", "/user/login").permitAll()
                        .anyRequest().permitAll()
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                /*.formLogin(login ->login
                        .loginPage("/user/login")
                        .permitAll()
                )*/
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
                )
                .addFilterBefore(new JwtTokenFilter(secretKey, userReadUseCase), UsernamePasswordAuthenticationFilter.class) //UserNamePasswordAuthenticationFilter적용하기 전에 JWTTokenFilter를 적용.
                .oauth2Login(oAuth -> oAuth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                ).exceptionHandling(ex ->
                        ex.authenticationEntryPoint(new AuthenticationEntryPointImpl())
                ).build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {//spring security에서 제외하겠다는 것
        //static resource 제외 (css, js ...)
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public UserDetailsService userDetailsService(UserAccountReadService userAccountReadService) {
        return username -> userAccountReadService.searchUser(username)
                .map(CarAppPrincipal::from)
                .orElseThrow(() -> {
                    throw new CarHrSnsAppException(USER_NOTFOUND_ACCOUNT, USER_NOTFOUND_ACCOUNT.getMessage());
                });
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(
            UserAccountReadService userAccountReadService,
            UserAccountWriteService userAccountWriteService
    ) {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            KakaoOAuth2Response kakaoOAuth2Response = KakaoOAuth2Response.from(oAuth2User.getAttributes());

            String registrationId = userRequest.getClientRegistration().getRegistrationId();// "kakao"
            String providerId = String.valueOf(kakaoOAuth2Response.id());
            String username = registrationId + "_" + providerId;
            //애플리케이션에서 비밀번호를 받아 주는 것이 아니라 전달할 이유는 없지만 entity 설계에 pw가 null 허용이 안되기 때문에 임의로 보내줌
            String dummyPassword = passwordEncoder.encode("dummy");

            //DB에 user가 있으면 반환, 없으면 저장
            return userAccountReadService.searchUser(username)
                    .map(CarAppPrincipal::from)
                    .orElseGet(() -> CarAppPrincipal.from(
                            userAccountWriteService.saveKakaoUserAccount(kakaoOAuth2Response, dummyPassword))
                    );
        };
    }

}
