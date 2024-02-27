package com.car.sns.infrastructure.jpaRepository;

import com.car.sns.domain.alarm.model.entity.Alarm;
import com.car.sns.domain.user.model.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmJpaRepository extends JpaRepository<Alarm, Long> {
    Page<Alarm> findByUserAccount(UserAccount userAccount, Pageable pageable);
}
