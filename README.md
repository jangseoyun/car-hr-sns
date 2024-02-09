# car-hr-sns

## ERD

``` mermaid
erDiagram
    ARTICLE ||--o{ ARTICLE_COMMENT : allows
    ARTICLE {
        bigint article_id PK "게시글 PK"
        varchar(255) title "제목" 
        varchar(65535) content "내용"
        varchar(255) hashtag "해시태그"
        datetime created_at "작성일"
        varchar(100) created_by "작성자"
        datetime modified_at "수정일"
        varchar(100) modified_by "수정자"
    }

    ARTICLE_COMMENT {
        bigint article_comment_id PK "댓글 PK"
        bigint article_id "게시글 ID"
        varchar(65535) content "내용"
        datetime created_at "작성일"
        varchar(100) created_by "작성자"
        datetime modified_at "수정일"
        varchar(100) modified_by "수정자"
    }
```

    
