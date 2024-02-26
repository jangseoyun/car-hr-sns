package com.car.sns.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    public static String createToken(String userId, String key, long expireTile) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) //지금 시간부터
                .setExpiration(new Date(System.currentTimeMillis() + expireTile)) //언제까지
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
