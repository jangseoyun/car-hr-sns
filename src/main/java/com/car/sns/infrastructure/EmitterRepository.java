package com.car.sns.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class EmitterRepository {
    private Map<String, SseEmitter> emitterMap = new HashMap<>();

    public SseEmitter save(String userId, SseEmitter sseEmitter) {
        final String key = getKey(userId);
        emitterMap.put(key, sseEmitter);
        log.info("save sse {} : {}", key, sseEmitter);
        return sseEmitter;
    }

    public Optional<SseEmitter> get(String userId) {
        final String key = getKey(userId);
        SseEmitter sseEmitter = emitterMap.get(key);
        log.info("get sse {} : {}", key, sseEmitter);
        return Optional.ofNullable(sseEmitter);
    }

    public void delete(String userId) {
        emitterMap.remove(getKey(userId));
    }

    private String getKey(String userId) {
        return "Emitter:UID" + userId;
    }
}
