package com.car.sns.application.usecase.alarm;

import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.alarm.model.AlarmType;

public interface AlarmManagementUseCase {
    AlarmDto alarmOccurred(AlarmType targetType, AlarmArgs alarmArgs, String toUserId);
}
