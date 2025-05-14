package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.client.media.MediaClient;
import com.hpmath.client.media.MediaClient.MediaInfo;
import com.hpmath.hpmathcoreapi.course.application.exception.MemoMediaException;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadMemoMediaByMemoMediaIdPort;
import com.hpmath.hpmathcoreapi.course.domain.MemoMedia;
import com.hpmath.hpmathcore.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LoadMemoMediaByMemoMediaIdAdapter implements LoadMemoMediaByMemoMediaIdPort {
    private final MemoMediaRepository memoMediaRepository;
    private final MediaClient mediaClient;

    @Override
    public MemoMedia loadByMemoMediaId(Long memoMediaId, Long memoId) {
        final com.hpmath.hpmathcoreapi.course.entity.MemoMedia memoMedia = memoMediaRepository.findMemoMediaByIdAndMemo_Id(memoMediaId, memoId)
                .orElseThrow(() -> new MemoMediaException("존재하지 않는 메모-미디어 입니다.", ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION));
        final MediaInfo mediaInfo = mediaClient.getFileInfo(memoMedia.getMediaSrc());
        return MemoMedia.createByEntity(
                memoMedia.getId(),
                mediaInfo.mediaName(),
                memoMedia.getMediaSrc(),
                null,
                memoMedia.getSequence()
        );
    }
}
