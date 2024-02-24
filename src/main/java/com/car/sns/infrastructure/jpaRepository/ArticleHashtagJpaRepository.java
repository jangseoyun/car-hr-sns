package com.car.sns.infrastructure.jpaRepository;

import com.car.sns.domain.hashtag.model.entity.ArticleHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleHashtagJpaRepository extends JpaRepository<ArticleHashtag, Long> {
}
