package com.car.sns.application.usecase;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterUseCase {
    SseEmitter connectAlarm(String userId);
    void send(String userId, Long alarmId);
}
