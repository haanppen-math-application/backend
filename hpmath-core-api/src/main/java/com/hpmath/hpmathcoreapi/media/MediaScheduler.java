package com.hpmath.hpmathcoreapi.media;

import com.hpmath.hpmathcoreapi.media.repository.ImageRepository;
import com.hpmath.hpmathcoreapi.media.repository.MediaRepository;
import com.hpmath.hpmathcoreapi.media.storage.MediaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaScheduler {

    private final MediaRepository mediaRepository;
    private final ImageRepository imageRepository;
    private final MediaStorage mediaStorage;

    @Scheduled(cron = "0 0 0 * * *")
    public void removeDeletedFiles() {
        mediaStorage.loadAllFileNames()
                .stream()
                .filter(fileName -> !mediaRepository.existsBySrc(fileName))
                .filter(fileName -> !imageRepository.existsBySrc(fileName))
                .forEach(fileName -> mediaStorage.remove(fileName));
    }
}
