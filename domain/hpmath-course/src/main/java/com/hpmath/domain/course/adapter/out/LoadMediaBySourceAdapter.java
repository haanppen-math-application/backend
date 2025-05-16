package com.hpmath.domain.course.adapter.out;

import com.hpmath.client.media.MediaClient;
import com.hpmath.client.media.MediaClient.MediaInfo;
import com.hpmath.domain.course.application.port.out.LoadMediaBySourcePort;
import com.hpmath.domain.course.domain.Media;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LoadMediaBySourceAdapter implements LoadMediaBySourcePort {

    private final MediaClient mediaClient;

    @Override
    public Media loadMediaBySource(String source) {
        if (Objects.isNull(source)) {
            throw new IllegalArgumentException();
        }

        final MediaInfo media = loadMedia(source);
        return Media.createByEntity(media.mediaName(), media.mediaSrc(), null);
    }

    private MediaInfo loadMedia(final String mediaSource) {
        return mediaClient.getFileInfo(mediaSource);
    }
}
