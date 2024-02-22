package com.car.sns.domain.hashtag.model;

import com.car.sns.domain.hashtag.entity.Hashtag;

/**
 * DTO for {@link com.car.sns.domain.hashtag.entity.Hashtag}
 */
public record HashtagDto(
        Long id,
        String hashtagName
) {

    public static HashtagDto of(Long id, String hashtagName) {
        return new HashtagDto(id, hashtagName);
    }

    public static HashtagDto toDto(Long id, String hashtagName) {
        return new HashtagDto(id, hashtagName);
    }

    public Hashtag toEntity() {
        return Hashtag.of(hashtagName);
    }
}