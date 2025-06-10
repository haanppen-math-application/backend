package com.hpmath.domain.notification;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.registeredAt < :before AND n.targetMemberId = :targetMemberId")
    List<Notification> queryWithCursor(
            @Param("targetMemberId") Long targetMemberId,
            @Param("before") LocalDateTime before,
            Pageable pageable
    );

    boolean existsByTargetMemberIdAndMessage(Long targetMemberId, String message);

    int countByTargetMemberIdAndReadAtIsNull(Long targetMemberId);
}
