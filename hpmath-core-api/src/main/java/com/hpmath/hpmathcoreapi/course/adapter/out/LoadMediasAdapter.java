package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.application.port.out.LoadMediasPort;
import com.hpmath.hpmathcoreapi.course.domain.Media;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import com.hpmath.hpmathcoreapi.media.exception.NoSuchMediaException;
import com.hpmath.hpmathcoreapi.media.repository.MediaRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadMediasAdapter implements LoadMediasPort {

    private final MediaRepository mediaRepository;

    @Override
    public List<Media> loadMedias(List<String> mediaSources) {
        final List<com.hpmath.hpmathcoreapi.media.entity.Media> medias = loadRelatedMedias(mediaSources);
        return medias.stream().map(media -> Media.createByEntity(media.getMediaName(), media.getSrc(), null))
                .collect(Collectors.toList());
    }

    private List<com.hpmath.hpmathcoreapi.media.entity.Media> loadRelatedMedias(final List<String> medias) {
        final List<com.hpmath.hpmathcoreapi.media.entity.Media> mediaEntities = mediaRepository.findAllBySrcIn(medias);
        if (medias.size() != mediaEntities.size()) {
            throw new NoSuchMediaException("미디어를 찾을 수 없거나, 중복된 파일이 존재합니다", ErrorCode.NO_SUCH_MEDIA);
        }
        return Collections.unmodifiableList(mediaEntities);
    }
}
