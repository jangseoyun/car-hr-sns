package com.car.sns.domain.board.repository.querydsl;

import com.car.sns.domain.hashtag.model.entity.Hashtag;
import com.car.sns.domain.hashtag.entity.QHashtag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class HashtagRepositoryCustomImpl extends QuerydslRepositorySupport implements HashtagRepositoryCustom {

    public HashtagRepositoryCustomImpl() {
        super(Hashtag.class);
    }

    @Override
    public List<String> findAllDistinctHashtag() {
        QHashtag hashtag = QHashtag.hashtag;

        return from(hashtag)
                .distinct()
                .select(hashtag.hashtagName)
                .fetch();
    }
}
