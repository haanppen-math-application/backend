package com.hpmath.domain.notification.dto;

import com.hpmath.domain.notification.Notification;
import jakarta.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * {@code NotificationResult} readAt 이 null인 데이터를 우선 합니다.
 * 이외의 경우, 등록날짜 기준 내림차순 정렬합니다.
 * @param notificationId
 * @param message
 * @param targetMemberId
 * @param registeredAt
 * @param readAt nullable
 */
public record NotificationResult(
        Long notificationId,
        String message,
        Long targetMemberId,
        LocalDateTime registeredAt,
        @Nullable
        LocalDateTime readAt
) implements Comparable<NotificationResult> {

    public static NotificationResult from(final Notification notification) {
        return new NotificationResult(
                notification.getId(),
                notification.getMessage(),
                notification.getTargetMemberId(),
                notification.getRegisteredAt(),
                notification.getReadAt());
    }

    @Override
    public int compareTo(NotificationResult o) {
        if (this.readAt == null && o.readAt != null) {
            return -1;
        }
        return o.registeredAt.compareTo(registeredAt);
    }
}
