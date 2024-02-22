package com.car.sns.domain.comment.entity;

import com.car.sns.common.AuditingFields;
import com.car.sns.domain.board.entity.Article;
import com.car.sns.domain.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


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

    @Column(name = "parent_comment_id", updatable = false)
    private Long parentCommentId;

    //TODO: cascade 보수적으로 도메인에 맞게 변경할 것
    @ToString.Exclude
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    private Set<ArticleComment> childComments = new LinkedHashSet<>();

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

    private ArticleComment(UserAccount userAccount, Long parentCommentId, Article article, String content) {
        this.userAccount = userAccount;
        this.article = article;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }

    public static ArticleComment of(UserAccount userAccount, Article article, String content) {
        return new ArticleComment(userAccount, null, article, content);
    }

    public void addChildComment(ArticleComment childComment) {
        childComment.updateParentCommentId(this.id);
        this.getChildComments().add(childComment);
    }

    private void updateParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return this.getId() != null && this.getId().equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
