package com.car.sns.kafka.consumer;

import com.car.sns.application.usecase.EmitterUseCase;
import com.car.sns.kafka.model.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final EmitterUseCase emitterUseCase;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consumeAlarm(AlarmEvent event, Acknowledgment ack) {
        log.info("consume the event alarm : {}", event);
        emitterUseCase.send(event.alarmType(), event.alarmArgs(), event.receiveUserId());
        ack.acknowledge();
    }
}
