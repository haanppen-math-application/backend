package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.exception.CourseException;
import com.hpmath.domain.course.application.port.out.UpdateMemoMediaPort;
import com.hpmath.domain.course.domain.Memo;
import com.hpmath.domain.course.entity.MemoMedia;
import com.hpmath.common.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMemoMediaAdapter implements UpdateMemoMediaPort {
    private final MemoMediaRepository memoMediaRepository;
    private final MemoRepository memoRepository;

    @Override
    public void update(Memo memo) {
        memoMediaRepository.deleteMemoMediaByMemo_Id(memo.getMemoId());
        final com.hpmath.domain.course.entity.Memo targetMemoEntity = memoRepository.findById(memo.getMemoId())
                .orElseThrow(() -> new CourseException("존재하지 않는 메모", ErrorCode.MEMO_NOT_EXIST));

        final List<MemoMedia> memoMedias = new ArrayList<>();
        for (int i = 0; i < memo.getMedias().size(); i++) {
            final com.hpmath.domain.course.domain.MemoMedia media = memo.getMedias().get(i);;
            final MemoMedia memoMedia = getSequenceMappedMediaEntity(targetMemoEntity, media.getMediaSource(), media.getSequence());
            memoMedias.add(memoMedia);
        }
        memoMediaRepository.saveAll(memoMedias);
    }

    private MemoMedia getSequenceMappedMediaEntity(final com.hpmath.domain.course.entity.Memo targetMemo, final String src, final Integer sequence) {
        return MemoMedia.of(targetMemo, src, sequence);
    }
}
