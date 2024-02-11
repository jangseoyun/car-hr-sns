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
        bigint article_id FK "게시글 ID"
        bigint user_account_id FK "사용자 계정 PK"
        varchar(65535) content "내용"
        datetime created_at "작성일"
        varchar(100) created_by "작성자"
        datetime modified_at "수정일"
        varchar(100) modified_by "수정자"
    }

USER_ACCOUNT ||--o{ ARTICLE : is
USER_ACCOUNT ||--o{ ARTICLE_COMMENT : is
    USER_ACCOUNT {
        bigint user_account_id PK "사용자 PK"
        varchar(50) user_id UK "사용자 ID"
        varchar(255) user_password "비밀번호"
        varchar(100) email "이메일"
        varchar(100) nickname "닉네임"
        varchar(255) memo "메모" 
        atetime created_at "작성일"
        varchar(100) created_by "작성자"
        datetime modified_at "수정일"
        varchar(100) modified_by "수정자"
    }
```

    
