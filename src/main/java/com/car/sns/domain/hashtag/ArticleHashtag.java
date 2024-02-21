package com.car.sns.domain.hashtag;

import com.car.sns.domain.board.entity.Article;
import com.car.sns.domain.hashtag.entity.Hashtag;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@Table(name = "t_article_hashtag")
public class ArticleHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleHashtag hashtag)) return false;
        return this.getId() != null && this.getId().equals(hashtag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
