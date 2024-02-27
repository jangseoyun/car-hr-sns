package com.car.sns.domain.alarm.service.read;

import com.car.sns.application.usecase.alarm.AlarmDto;
import com.car.sns.application.usecase.alarm.AlarmReadUseCase;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.infrastructure.jpaRepository.AlarmJpaRepository;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.car.sns.exception.model.AppErrorCode.USER_NOTFOUND_ACCOUNT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmReadService implements AlarmReadUseCase {

    private final UserAccountJpaRepository userAccountJpaRepository;
    private final AlarmJpaRepository alarmJpaRepository;

    /**
     * 특정 사용자의 알람 전체 리스트 조회
     */
    @Override
    public Page<AlarmDto> alarmList(String userId, Pageable pageable) {
        UserAccount toUserAccount = userAccountJpaRepository.findByUserId(userId).orElseThrow(() -> {
            throw new CarHrSnsAppException(USER_NOTFOUND_ACCOUNT, USER_NOTFOUND_ACCOUNT.getMessage());
        });
        Page<AlarmDto> alarmDtos = alarmJpaRepository.findByUserAccount(toUserAccount, pageable).map(AlarmDto::from);
        log.info("alarmlist: {}", alarmDtos);

        return alarmDtos;
    }
}
