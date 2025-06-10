package com.hpmath.domain.notification;

import com.hpmath.domain.notification.dto.NotificationResult;
import com.hpmath.domain.notification.dto.QueryPagedNotificationCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
@CacheConfig(cacheNames = "hpmath::domain::notification::not-read::count")
public class NotificationQueryService {
    private final NotificationRepository notificationRepository;

    public List<NotificationResult> queryWithPaged(@Valid final QueryPagedNotificationCommand command) {
        return notificationRepository.queryWithCursor(command.memberId(), command.before(), PageRequest.ofSize(command.pageSize())).stream()
                .map(NotificationResult::from)
                .sorted()
                .toList();
    }

    @Cacheable(key = "#memberId")
    public int queryNotReadCount(@NotNull final Long memberId) {
        return notificationRepository.countByTargetMemberIdAndReadAtIsNull(memberId);
    }
}
