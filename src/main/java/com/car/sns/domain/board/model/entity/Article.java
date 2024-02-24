package com.car.sns.domain.board.model.entity;

import com.car.sns.domain.comment.model.entity.ArticleComment;
import com.car.sns.common.AuditingFields;
import com.car.sns.domain.user.model.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@Table(name = "t_article",
        indexes = {
                @Index(columnList = "title"),
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")
        })
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Setter
    @Column(name = "title",
            nullable = false)
    private String title;

    @Setter
    @Column(name = "content",
            nullable = false,
            length = 10000)
    private String content;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<ArticleComment> articleComment = new HashSet<>();

    @ManyToOne(optional = false,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    private Article(UserAccount userAccount, String title, String content) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
    }

    public static Article of(UserAccount userAccount, String title, String content) {
        return new Article(userAccount, title, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return this.getId() != null && this.getId().equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
