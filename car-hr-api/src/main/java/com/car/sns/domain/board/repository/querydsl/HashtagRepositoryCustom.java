package com.car.sns.domain.board.repository.querydsl;

import java.util.List;

public interface HashtagRepositoryCustom {
    List<String> findAllDistinctHashtag();
}
