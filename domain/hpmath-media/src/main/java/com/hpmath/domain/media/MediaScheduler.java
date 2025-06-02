package com.hpmath.domain.media;

import com.hpmath.domain.media.repository.MediaRepository;
import com.hpmath.domain.media.storage.MediaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaScheduler {
    private final MediaRepository mediaRepository;
    private final MediaStorage mediaStorage;

    @Scheduled(cron = "0 0 0 * * *")
    public void removeDeletedFiles() {
        mediaStorage.loadAllFileNames()
                .stream()
                .filter(fileName -> !mediaRepository.existsBySrc(fileName))
                .forEach(mediaStorage::remove);
    }
}
