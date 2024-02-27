package com.car.sns.domain.like.model.entity;

import com.car.sns.common.AuditingFields;
import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.user.model.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@Table(name = "t_like")
public class Likes extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    private Likes(Long id, Article article, UserAccount userAccount) {
        this.id = id;
        this.article = article;
        this.userAccount = userAccount;
    }

    public static Likes of(Article article, UserAccount userAccount) {
        return new Likes(null, article, userAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Likes that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
