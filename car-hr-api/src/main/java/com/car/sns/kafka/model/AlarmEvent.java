package com.car.sns.kafka.model;

import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.alarm.model.AlarmType;

public record AlarmEvent(
        String receiveUserId,
        AlarmType alarmType,
        AlarmArgs alarmArgs
) {

}
