package com.car.sns.domain.alarm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    NEW_COMMENT_ON_ARTICLE      ("new comment!"),
    NEW_LIKE_ON_POST            ("someone like article!");

    private String alarmText;
}
