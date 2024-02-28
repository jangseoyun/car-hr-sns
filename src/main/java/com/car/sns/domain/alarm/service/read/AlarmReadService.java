package com.car.sns.domain.alarm.service.read;

import com.car.sns.application.usecase.alarm.AlarmDto;
import com.car.sns.application.usecase.alarm.AlarmReadUseCase;
import com.car.sns.infrastructure.jpaRepository.AlarmJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmReadService implements AlarmReadUseCase {

    private final AlarmJpaRepository alarmJpaRepository;

    /**
     * 특정 사용자의 알람 전체 리스트 조회
     */
    @Override
    public Page<AlarmDto> alarmList(String userId, Pageable pageable) {
        return alarmJpaRepository.findByUserAccount(userId, pageable).map(AlarmDto::from);
    }
}
