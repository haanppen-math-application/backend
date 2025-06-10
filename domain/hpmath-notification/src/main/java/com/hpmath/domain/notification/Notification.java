package com.hpmath.domain.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "notification",
        indexes = @Index(name = "idx_targetMemberId", columnList = "target_member_id asc, read_at asc, registered_at desc"))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "target_member_id")
    private Long targetMemberId; // shard key

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    public static Notification of(String message, Long targetMemberId, LocalDateTime registeredAt) {
        final Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTargetMemberId(targetMemberId);
        notification.setRegisteredAt(registeredAt);
        notification.setReadAt(null);

        return notification;
    }
}
