package com.hpmath.domain.notification;

import com.hpmath.domain.notification.dto.RegisterNotificationCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    @Transactional
    void 알림_등록은_멱등적으로_수행된다() {
        final long memberId = 1L;
        notificationService.add(new RegisterNotificationCommand("testMe11", memberId));
        notificationService.add(new RegisterNotificationCommand("testMe11", memberId));

        notificationService.add(new RegisterNotificationCommand("testMe12", memberId));
        notificationService.add(new RegisterNotificationCommand("testMe12", memberId));

        Assertions.assertEquals(2, notificationRepository.findAll().size());
    }
}