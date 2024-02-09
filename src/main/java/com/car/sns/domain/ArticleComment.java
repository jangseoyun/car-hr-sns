package com.car.sns.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id;
    private Article articleId;
    private String content;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;

}
