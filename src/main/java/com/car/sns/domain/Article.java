package com.car.sns.domain;

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
                @Index(columnList = "hashtag"),
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")
        })
public class Article extends AuditingFields{
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

    @Setter
    @Column(name = "hashtag")
    private String hashtag;

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

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
