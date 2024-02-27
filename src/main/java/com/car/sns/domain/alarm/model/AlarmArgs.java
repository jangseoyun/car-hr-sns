package com.car.sns.domain.alarm.model;

public record AlarmArgs(//알람을 발생시킨 사람과 타깃에 대한 정보
        String fromUserId,
        Long targetId
) {
    public static AlarmArgs of(String fromUserId, Long targetId) {
        return new AlarmArgs(fromUserId, targetId);
    }
}
