package com.hpmath.domain.media.service.uploadV2;

import com.hpmath.domain.media.storage.ChunkStorageV2;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ChunkGarbageCleaner {
    private final ChunkStorageV2 chunkStorageV2;
    private final ChunkInfoManager chunkInfoManager;

    @Scheduled(initialDelay = 10_000, fixedDelay = 1800_000)
    public void removeRemained() {
        log.info("started : Removing remained chunks");
        final List<String> uniqueIds = chunkStorageV2.loadAllUniqueIds();
        uniqueIds.stream()
                .filter(chunkInfoManager::notExist)
                .forEach(chunkStorageV2::removeUniqueId);
        log.info("finished : Removed {} remained uniqueIds", uniqueIds.size());
    }
}
