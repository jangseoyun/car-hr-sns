package com.car.sns.application.usecase;

import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.alarm.model.AlarmType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterUseCase {
    SseEmitter connectAlarm(String userId);
    void send(AlarmType alarmType, AlarmArgs alarmArgs, String receiverUserId);
}
