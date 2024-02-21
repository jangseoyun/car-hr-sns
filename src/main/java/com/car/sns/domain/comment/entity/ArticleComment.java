package com.car.sns.domain.comment.entity;

import com.car.sns.common.AuditingFields;
import com.car.sns.domain.user.entity.UserAccount;
import com.car.sns.domain.board.entity.Article;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@Table(name = "t_article_comment",
        indexes = {
                @Index(columnList = "content"),
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")
        })
public class ArticleComment extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_comment_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(name = "content",
            nullable = false,
            length = 500)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    private ArticleComment(UserAccount userAccount, Article article, String content) {
        this.userAccount = userAccount;
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(UserAccount userAccount, Article article, String content) {
        return new ArticleComment(userAccount, article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
