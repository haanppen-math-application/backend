package com.hpmath.hpmathmediadomain.media.service.uploadV2;

import com.hpmath.hpmathcore.TimeProvider;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class UploadIdLifeCycleManager {
    private final Map<String, LocalDateTime> idMap = new ConcurrentHashMap<>();
    private final TimeProvider timeProvider;
    private final Long expirationMinutes;

    public UploadIdLifeCycleManager(
            @Value("${server.chunk.expiration.minutes}") final Long expirationMinutes,
            final TimeProvider timeProvider
    ) {
        this.expirationMinutes = expirationMinutes;
        this.timeProvider = timeProvider;
    }

    synchronized void addUniqueId(final String uniqueId) {
        if (isExist(uniqueId)) {
            log.warn("Duplicate upload id found for uniqueId: {}", uniqueId);
            throw new IllegalArgumentException("Duplicated unique id: " + uniqueId);
        }
        log.debug("Adding unique id to map: {}", uniqueId);
        renewLastUpdateTime(uniqueId);
    }

    synchronized void updateLastAccessTime(final String uniqueId) {
        if (isExist(uniqueId)) {
            renewLastUpdateTime(uniqueId);
            return;
        }
        log.debug("Updating last access time for uniqueId: {}", uniqueId);
        throw new IllegalArgumentException("Unique Id not Exist it might be removed by time out");
    }

    synchronized void remove(final String uniqueId) {
        idMap.remove(uniqueId);
    }

    List<String> getExpiredIds(final LocalDateTime now) {
        final List<String> expiredIds = new ArrayList<>();
        for (final Map.Entry<String, LocalDateTime> entry : idMap.entrySet()) {
            final String uniqueId = entry.getKey();
            final LocalDateTime lastUpdatedTime = entry.getValue();
            if (isExpired(now, lastUpdatedTime)) {
                expiredIds.add(uniqueId);
            }
        }
        return expiredIds;
    }

    private boolean isExpired(final LocalDateTime now, final LocalDateTime lastUpdatedTime) {
        return lastUpdatedTime.plus(expirationMinutes, TimeUnit.MINUTES.toChronoUnit()).isBefore(now);
    }

    private boolean isExist(final String uniqueId) {
        return idMap.containsKey(uniqueId);
    }

    private void renewLastUpdateTime(final String uniqueId) {
        idMap.put(uniqueId, timeProvider.getCurrentTime());
    }
}
