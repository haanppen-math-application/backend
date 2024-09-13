package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.port.out.UpdateMemoMediaContainerPort;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.course.domain.MemoMediaContainer;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.exception.MediaException;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class UpdateMemoMediaContainerAdapter implements UpdateMemoMediaContainerPort {
    private final MemoMediaRepository memoMediaRepository;
    private final MediaRepository mediaRepository;
    private final MemoRepository memoRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(MemoMediaContainer memoMediaContainer) {
        final List<MemoMedia> unmodifiableMedias = memoMediaContainer.getMemoMedias();
        unmodifiableMedias.stream()
                .forEach(memoMedia -> this.update(memoMedia, memoMediaContainer.getMemoId()));
    }

    private void update(final MemoMedia memoMediaDomain, final Long targetMemoId) {
        final com.hanpyeon.academyapi.course.adapter.out.MemoMedia memoMedia = memoMediaRepository.findMemoMediaByMedia_SrcAndMemo_Id(memoMediaDomain.getMediaSource(), targetMemoId)
                .orElse(com.hanpyeon.academyapi.course.adapter.out.MemoMedia.of(loadMemo(targetMemoId), loadMedia(memoMediaDomain.getMediaSource()), memoMediaDomain.getSequence()));
        memoMedia.setSequence(memoMediaDomain.getSequence());
        memoMediaRepository.save(memoMedia);
    }

    private Memo loadMemo(final Long memoId) {
        return memoRepository.findById(memoId)
                .orElseThrow(() -> new CourseException(ErrorCode.MEMO_NOT_EXIST));
    }

    private Media loadMedia(final String mediaSource) {
        return mediaRepository.findBySrc(mediaSource)
                .orElseThrow(() -> new MediaException(ErrorCode.NO_SUCH_MEDIA));
    }
}
