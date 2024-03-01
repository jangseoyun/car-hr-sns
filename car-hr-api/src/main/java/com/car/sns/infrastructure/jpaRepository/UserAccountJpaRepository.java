package com.car.sns.infrastructure.jpaRepository;

import com.car.sns.domain.user.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountJpaRepository extends JpaRepository<UserAccount, Long> {
    @Query("select u from UserAccount  u where u.userId = :userId")
    Optional<UserAccount> findByUserId(@Param("userId") String userId);
}