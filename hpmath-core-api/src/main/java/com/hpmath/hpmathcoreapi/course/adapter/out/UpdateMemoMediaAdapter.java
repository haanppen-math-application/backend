package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.application.exception.CourseException;
import com.hpmath.hpmathcoreapi.course.application.port.out.UpdateMemoMediaPort;
import com.hpmath.hpmathcoreapi.course.domain.Memo;
import com.hpmath.hpmathcoreapi.course.entity.MemoMedia;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import com.hpmath.hpmathcoreapi.media.entity.Media;
import com.hpmath.hpmathcoreapi.media.exception.NoSuchMediaException;
import com.hpmath.hpmathcoreapi.media.repository.MediaRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMemoMediaAdapter implements UpdateMemoMediaPort {

    private final MemoMediaRepository memoMediaRepository;
    private final MemoRepository memoRepository;
    private final MediaRepository mediaRepository;

    @Override
    public void update(Memo memo) {
        memoMediaRepository.deleteMemoMediaByMemo_Id(memo.getMemoId());
        final com.hpmath.hpmathcoreapi.course.entity.Memo targetMemoEntity = memoRepository.findById(memo.getMemoId())
                .orElseThrow(() -> new CourseException("존재하지 않는 메모", ErrorCode.MEMO_NOT_EXIST));

        final List<MemoMedia> memoMedias = new ArrayList<>();
        for (int i = 0; i < memo.getMedias().size(); i++) {
            final com.hpmath.hpmathcoreapi.course.domain.MemoMedia media = memo.getMedias().get(i);;
            final MemoMedia memoMedia = getSequenceMappedMediaEntity(targetMemoEntity, media.getMediaSource(), media.getSequence());
            memoMedias.add(memoMedia);
        }
        memoMediaRepository.saveAll(memoMedias);
    }

    private MemoMedia getSequenceMappedMediaEntity(final com.hpmath.hpmathcoreapi.course.entity.Memo targetMemo, final String src, final Integer sequence) {
        final Media media = mediaRepository.findBySrc(src)
                .orElseThrow(() -> new NoSuchMediaException("해당 미디어를 찾을 수 없습니다.",ErrorCode.NO_SUCH_MEDIA));
        return MemoMedia.of(targetMemo, media, sequence);
    }
}
