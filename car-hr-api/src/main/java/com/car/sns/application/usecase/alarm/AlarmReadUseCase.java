package com.car.sns.application.usecase.alarm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlarmReadUseCase {
    Page<AlarmDto> alarmList(String userId, Pageable pageable);
}
