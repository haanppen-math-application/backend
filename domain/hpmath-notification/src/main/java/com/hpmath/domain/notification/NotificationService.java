package com.hpmath.domain.notification;

import com.hpmath.domain.notification.port.NotificationRegisteredEvent;
import com.hpmath.domain.notification.dto.ReadNotificationCommand;
import com.hpmath.domain.notification.dto.RegisterNotificationCommand;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
@CacheConfig(cacheNames = "hpmath::domain::notification::not-read::count")
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @CacheEvict(key = "#command.targetMemberId()", condition = "#command.targetMemberId() != null")
    public void add(@Valid final RegisterNotificationCommand command) {
        if (command.targetMemberId() == null) {
            return;
        }

        if (isPresent(command.message(), command.targetMemberId())) {
            log.warn("Notification already registered: {}", command);
            return;
        }
        final Notification notification = Notification.of(command.message(), command.targetMemberId(), LocalDateTime.now());
        notificationRepository.save(notification);

        eventPublisher.publishEvent(NotificationRegisteredEvent.from(notification));
    }

    @Transactional
    @CacheEvict(key = "#command.memberId()")
    public void markAsRead(@Valid final ReadNotificationCommand command) {
        notificationRepository.updateReadAt(command.notificationId(), command.memberId(), LocalDateTime.now());
    }

    private boolean isPresent(final String message, final Long targetMemberId) {
        return notificationRepository.existsByTargetMemberIdAndMessage(targetMemberId, message);
    }
}
