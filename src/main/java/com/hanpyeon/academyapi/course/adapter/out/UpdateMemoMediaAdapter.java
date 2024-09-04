package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.port.out.UpdateMemoMediaPort;
import com.hanpyeon.academyapi.course.domain.Memo;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.exception.NoSuchMediaException;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateMemoMediaAdapter implements UpdateMemoMediaPort {

    private final MemoMediaRepository memoMediaRepository;
    private final MemoRepository memoRepository;
    private final MediaRepository mediaRepository;

    @Override
    public void update(Memo memo) {
        memoMediaRepository.deleteMemoMediaByMemo_Id(memo.getMemoId());
        final com.hanpyeon.academyapi.course.adapter.out.Memo targetMemoEntity = memoRepository.findById(memo.getMemoId())
                .orElseThrow(() -> new CourseException("존재하지 않는 메모", ErrorCode.MEMO_NOT_EXIST));
        final List<com.hanpyeon.academyapi.course.adapter.out.MemoMedia> memoMedias = new ArrayList<>();
        final List<Media> relatedMedias = loadRelatedMedias(memo.getMedias());
        for (int i = 0; i < relatedMedias.size() ; i++) {
            memoMedias.add(com.hanpyeon.academyapi.course.adapter.out.MemoMedia.of(targetMemoEntity, relatedMedias.get(i), i));
        }
        memoMediaRepository.saveAll(memoMedias);
    }

    private List<Media> loadRelatedMedias(final List<MemoMedia> medias) {
        final List<String> mediaSources = medias.stream()
                .map(media -> media.getMediaSource())
                .collect(Collectors.toList());
        final List<Media> mediaEntities = mediaRepository.findAllBySrcIn(mediaSources);
        if (medias.size() != mediaEntities.size()) {
            throw new NoSuchMediaException(ErrorCode.NO_SUCH_MEDIA);
        }
        return Collections.unmodifiableList(mediaEntities);
    }
}
