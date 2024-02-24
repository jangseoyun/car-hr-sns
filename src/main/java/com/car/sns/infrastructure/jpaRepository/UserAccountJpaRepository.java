package com.car.sns.infrastructure.jpaRepository;

import com.car.sns.domain.user.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountJpaRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUserId(String userId);
}