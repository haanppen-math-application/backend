package com.hpmath.domain.media.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.media.dto.MediaInfo;
import com.hpmath.domain.media.exception.MediaException;
import com.hpmath.domain.media.repository.MediaRepository;
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
