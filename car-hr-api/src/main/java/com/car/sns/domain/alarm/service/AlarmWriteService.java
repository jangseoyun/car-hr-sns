package com.car.sns.domain.alarm.service;

import com.car.sns.application.usecase.alarm.AlarmDto;
import com.car.sns.application.usecase.alarm.AlarmManagementUseCase;
import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.alarm.model.AlarmType;
import com.car.sns.domain.alarm.model.entity.Alarm;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.infrastructure.jpaRepository.AlarmJpaRepository;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.car.sns.exception.model.AppErrorCode.USER_NOTFOUND_ACCOUNT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AlarmWriteService implements AlarmManagementUseCase {

    private final AlarmJpaRepository alarmJpaRepository;
    private final UserAccountJpaRepository userAccountJpaRepository;
    @Override
    public AlarmDto alarmOccurred(AlarmType alarmType, AlarmArgs alarmArgs, String toUserId) {
        UserAccount toUser = userAccountJpaRepository.findByUserId(toUserId).orElseThrow(() -> {
            throw new CarHrSnsAppException(USER_NOTFOUND_ACCOUNT, USER_NOTFOUND_ACCOUNT.getMessage());
        });

        Alarm savedAlarm = alarmJpaRepository.save(Alarm.of(toUser, alarmType, alarmArgs));
        return AlarmDto.from(savedAlarm);
    }
}
