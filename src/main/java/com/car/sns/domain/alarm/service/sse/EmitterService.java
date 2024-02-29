package com.car.sns.domain.alarm.service.sse;

import com.car.sns.application.usecase.EmitterUseCase;
import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.alarm.model.AlarmType;
import com.car.sns.domain.alarm.model.entity.Alarm;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.infrastructure.EmitterRepository;
import com.car.sns.infrastructure.jpaRepository.AlarmJpaRepository;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.car.sns.exception.model.AppErrorCode.NOTIFICATION_CONNECT_ERROR;
import static com.car.sns.exception.model.AppErrorCode.USER_NOTFOUND_ACCOUNT;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmitterService implements EmitterUseCase {
    private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final static String ALARM_NAME = "alarm";
    private final EmitterRepository emitterRepository;
    private final AlarmJpaRepository alarmJpaRepository;
    private final UserAccountJpaRepository userAccountJpaRepository;


    @Override
    public SseEmitter connectAlarm(String userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        //connect
        emitterRepository.save(userId, sseEmitter);
        //종료했을 때 실행할 동작
        sseEmitter.onCompletion(() -> emitterRepository.delete(userId));
        //타임아웃일 때 실행할 동작
        sseEmitter.onTimeout(() -> emitterRepository.delete(userId));

        try {
            //연결됐다는 이벤트를 보낸다
            sseEmitter.send(SseEmitter.event().id("id").name("").data("connect complated"));
        } catch (IOException e) {
            emitterRepository.delete(userId);
            throw new CarHrSnsAppException(NOTIFICATION_CONNECT_ERROR, NOTIFICATION_CONNECT_ERROR.getMessage());
        }
        return sseEmitter;
    }

    //sse 이벤트를 보낸다
    @Override
    public void send(AlarmType alarmType, AlarmArgs alarmArgs, String receiverUserId) {
        UserAccount receiverUser = userAccountJpaRepository.findByUserId(receiverUserId).orElseThrow(() -> {
            throw new CarHrSnsAppException(USER_NOTFOUND_ACCOUNT, USER_NOTFOUND_ACCOUNT.getMessage());
        });
        //alarm save & sse emitter
        Alarm alarm = alarmJpaRepository.save(Alarm.of(receiverUser, alarmType, alarmArgs));
        emitterRepository.get(receiverUserId).ifPresentOrElse(sseEmitter -> {
                    try {
                        sseEmitter.send(SseEmitter.event().id(alarm.getId().toString()).name(ALARM_NAME).data("new alarm!!"));
                    } catch (IOException e) {
                        throw new CarHrSnsAppException(NOTIFICATION_CONNECT_ERROR, NOTIFICATION_CONNECT_ERROR.getMessage());
                    }
                }, () -> log.info("no emitter founded")
        );
    }

}
