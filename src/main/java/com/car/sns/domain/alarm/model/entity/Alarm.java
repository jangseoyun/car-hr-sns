package com.car.sns.domain.alarm.model.entity;

import com.car.sns.common.AuditingFields;
import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.alarm.model.AlarmType;
import com.car.sns.domain.user.model.entity.UserAccount;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@Table(name = "t_alarm")
public class Alarm extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type")
    private AlarmType alarmType;

    @Type(JsonType.class)
    @Column(name = "alarm_args",
            columnDefinition = "longtext")
    private AlarmArgs alarmArgs;

    private Alarm(Long id, UserAccount userAccount, AlarmType alarmType, AlarmArgs alarmArgs) {
        this.id = id;
        this.userAccount = userAccount;
        this.alarmType = alarmType;
        this.alarmArgs = alarmArgs;
    }

    public static Alarm of(UserAccount userAccount, AlarmType alarmType, AlarmArgs alarmArgs) {
        return new Alarm(null, userAccount, alarmType, alarmArgs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alarm that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
