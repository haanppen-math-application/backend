package com.hpmath.hpmathmediadomain.media.service;

import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathmediadomain.media.dto.MediaInfo;
import com.hpmath.hpmathmediadomain.media.exception.MediaException;
import com.hpmath.hpmathmediadomain.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaQueryService {
    private final MediaRepository mediaRepository;

    public MediaInfo getMediaInfo(String mediaSrc) {
        return mediaRepository.findMediaInfo(mediaSrc)
                .orElseThrow(() -> new MediaException(ErrorCode.NO_SUCH_MEDIA));
    }
}
