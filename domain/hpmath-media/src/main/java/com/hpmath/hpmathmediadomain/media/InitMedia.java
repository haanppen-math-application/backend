package com.hpmath.hpmathmediadomain.media;

import com.hpmath.hpmathmediadomain.media.entity.Media;
import com.hpmath.hpmathmediadomain.media.repository.MediaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitMedia {
    private final MediaRepository mediaRepository;
    @PostConstruct
    void init() {
        mediaRepository.save(new Media("testMediaName", "testSrc", 1L, 10L, 100000L));
    }
}
