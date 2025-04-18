package com.hpmath.hpmathmediadomain.media.service.uploadV2;

import com.hpmath.hpmathcore.TimeProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ChunkGarbageCleaner {
    private final ChunkPartNumberManager chunkNumberManager;
    private final ChunkStorageV2 chunkStorageV2;
    private final UploadIdLifeCycleManager uniqueIdManger;
    private final TimeProvider timeProvider;

    @Scheduled(initialDelay = 10_000, fixedDelay = 1800_000)
    public void removeRemained() {
        log.info("started : Removing remained chunks");
        final List<String> removable = uniqueIdManger.getExpiredIds(timeProvider.getCurrentTime());
        removable.forEach(uniqueId -> {
            uniqueIdManger.remove(uniqueId);
            chunkNumberManager.remove(uniqueId);
            chunkStorageV2.removeRelated(uniqueId);
        });
        log.info("finished : Removed {} remained chunks", removable.size());
    }
}
