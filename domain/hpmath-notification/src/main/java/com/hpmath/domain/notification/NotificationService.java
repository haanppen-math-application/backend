package com.hpmath.domain.notification;

import com.hpmath.domain.notification.dto.ReadNotificationCommand;
import com.hpmath.domain.notification.dto.RegisterNotificationCommand;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional
    public void add(@Valid final RegisterNotificationCommand command) {
        if (isPresent(command.message(), command.targetMemberId())) {
            log.warn("Notification already registered: {}", command);
            return;
        }
        final Notification notification = Notification.of(command.message(), command.targetMemberId(), LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAsRead(@Valid final ReadNotificationCommand command) {
        notificationRepository.findById(command.notificationId())
                .ifPresent(notification -> notification.setReadAt(LocalDateTime.now()));
    }

    private boolean isPresent(final String message, final Long targetMemberId) {
        return notificationRepository.existsByTargetMemberIdAndMessage(targetMemberId, message);
    }
}
