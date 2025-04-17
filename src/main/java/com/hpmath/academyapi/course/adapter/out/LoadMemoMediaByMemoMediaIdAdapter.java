package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.course.application.exception.MemoMediaException;
import com.hpmath.academyapi.course.application.port.out.LoadMemoMediaByMemoMediaIdPort;
import com.hpmath.academyapi.course.domain.MemoMedia;
import com.hpmath.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LoadMemoMediaByMemoMediaIdAdapter implements LoadMemoMediaByMemoMediaIdPort {
    private final MemoMediaRepository memoMediaRepository;
    @Override
    public MemoMedia loadByMemoMediaId(Long memoMediaId, Long memoId) {
        final com.hpmath.academyapi.course.entity.MemoMedia memoMedia = memoMediaRepository.findMemoMediaByIdAndMemo_Id(memoMediaId, memoId)
                .orElseThrow(() -> new MemoMediaException("존재하지 않는 메모-미디어 입니다.", ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION));
        return MemoMedia.createByEntity(
                memoMedia.getId(),
                memoMedia.getMedia().getMediaName(),
                memoMedia.getMedia().getSrc(),
                null,
                memoMedia.getSequence()
        );
    }
}
