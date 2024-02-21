package com.car.sns.infrastructure.repository;

import com.car.sns.domain.board.entity.Article;
import com.car.sns.domain.board.repository.querydsl.HashtagRepositoryCustom;
import com.car.sns.domain.hashtag.entity.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HashtagJpaRepository extends JpaRepository<Hashtag, Long> , HashtagRepositoryCustom {
    @Query("select a from Hashtag a where a.hashtagName LIKE CONCAT('%',:hashtag,'%')")
    Page<Article> findByHashtag(@Param("hashtag") String hashtag, Pageable pageable);
}
