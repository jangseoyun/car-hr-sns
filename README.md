# car-hr-sns

## ERD

``` mermaid
erDiagram 
ARTICLE ||--o{ ARTICLE_COMMENT : is
    ARTICLE {
        bigint article_id PK "게시글 PK"
        varchar(255) title "제목" 
        varchar(65535) content "내용"
        datetime created_at "작성일"
        varchar(100) created_by "작성자"
        datetime modified_at "수정일"
        varchar(100) modified_by "수정자"
    }

    ARTICLE_COMMENT {
        bigint article_comment_id PK "댓글 PK"
        bigint article_id FK "게시글 ID"
        bigint user_account_id FK "사용자 계정 PK"
        bigint parent_comment_id "부모 댓글 id"
        varchar(65535) content "내용"
        datetime created_at "작성일"
        varchar(100) created_by "작성자"
        datetime modified_at "수정일"
        varchar(100) modified_by "수정자"
    }
    
    ARTICLE_HASHTAG {
        bigint article_hashtag_id PK "게시글-해시태그-매핑 PK"
        bigint article_id FK "게시글 PK"
        bigint hashtag_id FK "해시태그 PK"
    }
    
ARTICLE ||--|| ARTICLE_HASHTAG : is
ARTICLE_HASHTAG ||--|| HASHTAG : is
    HASHTAG {
        bigint hashtag_id PK "해시태그 PK"
        varchar(50) hashtag_name UK "해시태그"
        atetime created_at "작성일"
        varchar(100) created_by "작성자"
        datetime modified_at "수정일"
        varchar(100) modified_by "수정자"
    }

USER_ACCOUNT ||--o{ ARTICLE : allows
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
USER_ACCOUNT ||--o{ LIKE : allows
ARTICLE_COMMENT ||--o{ LIKE : allows
    LIKE {
        bigint like_id PK "좋아요 PK"
        bigint user_account_id FK "좋아요를 발생시긴 사용자 PK"
        bigint article_id FK "좋아요가 발생한 게시글 주체 PK"
        atetime created_at "작성일"
        varchar(100) created_by "작성자"
        datetime modified_at "수정일"
        varchar(100) modified_by "수정자"
    }
USER_ACCOUNT ||--o{ ALARM : allows   
    ALARM {
        bigint alarm_id PK "알람 PK"
        bigint user_account_id FK "알람을 받을 사용자 PK"
        varchar(50) alarm_type "알람 타입 (enum)"
        json alarm_args "알람이 발생한 주체 object"
        atetime created_at "작성일"
        varchar(100) created_by "작성자"
        datetime modified_at "수정일"
        varchar(100) modified_by "수정자"
    }
   
```

    
