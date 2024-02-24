package com.car.sns.domain.board.repository;

import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.board.entity.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long>
        , QuerydslPredicateExecutor<Article>
        , QuerydslBinderCustomizer<QArticle> {

    @Query("select a from Article a where a.title LIKE CONCAT('%',:title,'%')")
    Page<Article> findByTitleContaining(@Param("title") String title, Pageable pageable);
    @Query("select a from Article a where a.content LIKE CONCAT('%',:content,'%')")
    Page<Article> findByContentContaining(@Param("content") String content, Pageable pageable);
    @Query("select a from Article a where a.userAccount.userId LIKE CONCAT('%',:userId,'%')")
    Page<Article> findByUserAccount_UserIdContaining(@Param("userId") String userId, Pageable pageable);
    @Query("select a from Article a where a.createdBy LIKE CONCAT('%',:nickname,'%')")
    Page<Article> findByUserAccount_NicknameContaining(@Param("nickname") String nickname, Pageable pageable);

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.createdAt, root.createdBy);

        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::likeIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::likeIgnoreCase);
    }
}