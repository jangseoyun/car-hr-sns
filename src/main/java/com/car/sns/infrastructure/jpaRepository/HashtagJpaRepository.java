package com.car.sns.infrastructure.jpaRepository;

import com.car.sns.domain.board.repository.querydsl.HashtagRepositoryCustom;
import com.car.sns.domain.hashtag.model.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface HashtagJpaRepository extends JpaRepository<Hashtag, Long> , HashtagRepositoryCustom {
    @Query("select a.id from Hashtag a where a.hashtagName LIKE CONCAT('%',:hashtag,'%')")
    Set<Long> findByHashtag(@Param("hashtag") String hashtag);

    Set<String> findByHashtagNameIn(Set<String> hashtagNames);
}
