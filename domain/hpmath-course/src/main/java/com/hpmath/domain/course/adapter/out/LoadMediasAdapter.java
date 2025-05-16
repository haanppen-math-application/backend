package com.hpmath.domain.course.adapter.out;

import com.hpmath.client.media.MediaClient;
import com.hpmath.client.media.MediaClient.MediaInfo;
import com.hpmath.domain.course.domain.Media;
import com.hpmath.common.ErrorCode;
import com.hpmath.domain.course.application.exception.CourseException;
import com.hpmath.domain.course.application.port.out.LoadMediasPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadMediasAdapter implements LoadMediasPort {
    private final MediaClient mediaClient;

    @Override
    public List<Media> loadMedias(List<String> mediaSources) {
        final List<MediaInfo> medias = loadRelatedMedias(mediaSources);
        return medias.stream().map(Media::from)
                .collect(Collectors.toList());
    }

    private List<MediaInfo> loadRelatedMedias(final List<String> mediaSrcs) {
        final List<MediaInfo> mediaEntities = mediaSrcs.stream()
                .parallel()
                .map(mediaClient::getFileInfo)
                .toList();
        if (mediaSrcs.size() != mediaEntities.size()) {
            throw new CourseException("미디어를 찾을 수 없거나, 중복된 파일이 존재합니다", ErrorCode.NO_SUCH_MEDIA);
        }
        return mediaEntities;
    }
}
