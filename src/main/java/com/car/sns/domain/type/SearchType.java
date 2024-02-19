package com.car.sns.domain.type;

import lombok.Getter;

public enum SearchType {
    CONTENT("본문"),
    HASHTAG("해시태그"),
    TITLE("제목"),
    ID("사용자 ID"),
    NICKNAME("닉네임");

    @Getter
    private final String description;

    SearchType(String description) {
        this.description = description;
    }
}
