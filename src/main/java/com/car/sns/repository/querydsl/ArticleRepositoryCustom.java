package com.car.sns.repository.querydsl;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<String> findAllDistinctHashtag();
}
