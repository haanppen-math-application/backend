package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.exception.MemoMediaException;
import com.hpmath.domain.course.application.port.out.UpdateMemoMediaContainerPort;
import com.hpmath.domain.course.domain.MemoMedia;
import com.hpmath.domain.course.domain.MemoMediaContainer;
import com.hpmath.common.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class UpdateMemoMediaContainerAdapter implements UpdateMemoMediaContainerPort {
    private final MemoMediaRepository memoMediaRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(MemoMediaContainer memoMediaContainer) {
        final List<MemoMedia> unmodifiableMedias = memoMediaContainer.getMemoMedias();

        unmodifiableMedias.stream()
                .forEach(memoMedia -> this.update(memoMedia));
    }

    private void update(final MemoMedia memoMediaDomain) {
        log.debug(memoMediaDomain.toString());
        final com.hpmath.domain.course.entity.MemoMedia memoMedia = memoMediaRepository.findById(memoMediaDomain.getMemoMediaId())
                        .orElseThrow(() -> new MemoMediaException("해당 수업 미디어를 찾을 수 없음", ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION));
        log.debug(memoMedia.toString());
        memoMedia.setSequence(memoMediaDomain.getSequence());
        log.debug(memoMedia.toString());
    }
}
