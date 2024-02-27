package com.car.sns.infrastructure.jpaRepository;

import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.like.model.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeJpaRepository extends JpaRepository<Likes, Long> {
    @Query(value = "select count(likes) from Likes likes where likes.article = :article")
    int findByLikeCount(@Param("article") Article article);
}
