package com.car.sns.infrastructure.jpaRepository;

import com.car.sns.domain.alarm.model.entity.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlarmJpaRepository extends JpaRepository<Alarm, Long> {
    @Query("select a from Alarm a where a.userAccount.userId = :userId")
    Page<Alarm> findByUserAccount(@Param("userId") String userId, Pageable pageable);
}
