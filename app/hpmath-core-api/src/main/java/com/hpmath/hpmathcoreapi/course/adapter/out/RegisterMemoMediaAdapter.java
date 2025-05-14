package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.client.media.MediaClient;
import com.hpmath.client.media.MediaClient.MediaInfo;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcoreapi.course.application.exception.MemoMediaException;
import com.hpmath.hpmathcoreapi.course.application.port.out.RegisterMemoMediaPort;
import com.hpmath.hpmathcoreapi.course.entity.Memo;
import com.hpmath.hpmathcoreapi.course.entity.MemoMedia;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class RegisterMemoMediaAdapter implements RegisterMemoMediaPort {
    private final MemoRepository memoRepository;
    private final MemoMediaRepository memoMediaRepository;
    private final MediaClient mediaClient;

    @Override
    @Transactional
    public void register(String mediaSource, Long memoId) {
        final MediaInfo mediaInfo = mediaClient.getFileInfo(mediaSource);
        final Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoMediaException(ErrorCode.MEMO_NOT_EXIST));
        final List<MemoMedia> memoMedias = memoMediaRepository.findAllByMemo_Id(memoId);
        memoMediaRepository.save(MemoMedia.of(memo, mediaInfo.mediaSrc(), memoMedias.size()));
    }
}
