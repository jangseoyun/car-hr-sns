package com.car.sns.application.usecase.alarm;

import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.alarm.model.AlarmType;
import com.car.sns.domain.alarm.model.entity.Alarm;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.car.sns.domain.alarm.model.entity.Alarm}
 */
public record AlarmDto(
        LocalDateTime createdAt,
        AlarmType alarmType,
        AlarmArgs alarmArgs,
        String alarmText
) {
    public static AlarmDto from(Alarm alarm) {
        return new AlarmDto(
                alarm.getCreatedAt(),
                alarm.getAlarmType(),
                alarm.getAlarmArgs(),
                alarm.getAlarmType().getAlarmText());
    }
}