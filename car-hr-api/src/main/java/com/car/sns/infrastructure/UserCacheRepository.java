package com.car.sns.infrastructure;

import com.car.sns.security.CarAppPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository {
    private final RedisTemplate<String, CarAppPrincipal> userRedisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);
    private final ObjectMapper objectMapper;

    public void setUser(CarAppPrincipal carAppPrincipal) {
        String key = getKey(carAppPrincipal.getUsername());
        log.info("set user to redis {} : {}", key, carAppPrincipal);
        userRedisTemplate.opsForValue().set(key, carAppPrincipal, USER_CACHE_TTL);
    }

    @Transactional(readOnly = true)
    public Optional<CarAppPrincipal> getUser(String userId) {
        String key = getKey(userId);
        CarAppPrincipal carAppPrincipal = userRedisTemplate.opsForValue().get(key);
        log.info("get user from redis {} : {}", key, carAppPrincipal);
        return Optional.of(carAppPrincipal);
    }

    public String getKey(String userId) {//필터단에서 userId를 사용하기 때문에 userId로 설정
        return "USER:" + userId;
    }
}
