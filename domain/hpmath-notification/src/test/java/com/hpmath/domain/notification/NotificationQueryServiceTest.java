package com.hpmath.domain.notification;

import com.hpmath.domain.notification.dto.QueryPagedNotificationCommand;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class NotificationQueryServiceTest {
    @Autowired
    private NotificationQueryService notificationQueryService;
    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    @Test
    void 특정_사용자의_알림만_조회한다() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        for (int i = 0; i < 10; i++) {
            notificationRepository.save(Notification.of("test", 1L, now.minusDays(i)));
        }
        for (int i = 0; i < 100; i++) {
            notificationRepository.save(Notification.of("test", 2L, now.minusDays(i)));
        }

        Assertions.assertEquals(
                10,
                notificationQueryService.queryWithPaged(new QueryPagedNotificationCommand(1L, now.plusDays(1), 100)).size()
        );
    }

    @Transactional
    @Test
    void 특정_시간_이전의_알림만_조회한다() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        for (int i = 0; i < 10; i++) {
            notificationRepository.save(Notification.of("test", 1L, now.plusDays(i)));
        }

        Assertions.assertEquals(
                5,
                notificationQueryService.queryWithPaged(new QueryPagedNotificationCommand(1L, now.plusDays(5), 100)).size()
        );
    }

    @Transactional
    @Test
    void 페이지_갯수만_조회한다() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        for (int i = 0; i < 10; i++) {
            notificationRepository.save(Notification.of("test", 1L, now.plusDays(i)));
        }

        Assertions.assertEquals(
                3,
                notificationQueryService.queryWithPaged(new QueryPagedNotificationCommand(1L, now.plusDays(5), 3)).size()
        );
    }

    @Test
    void 쿼리_100개_초과는_에러() {
        Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> notificationQueryService.queryWithPaged(new QueryPagedNotificationCommand(1L, LocalDateTime.now(), 101)));
    }
}