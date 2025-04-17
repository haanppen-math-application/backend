package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.course.application.exception.MemoMediaException;
import com.hpmath.academyapi.course.application.port.out.RegisterMemoMediaPort;
import com.hpmath.academyapi.course.entity.Memo;
import com.hpmath.academyapi.course.entity.MemoMedia;
import com.hpmath.academyapi.exception.ErrorCode;
import com.hpmath.academyapi.media.entity.Media;
import com.hpmath.academyapi.media.exception.NoSuchMediaException;
import com.hpmath.academyapi.media.repository.MediaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class RegisterMemoMediaAdapter implements RegisterMemoMediaPort {
    private final MediaRepository mediaRepository;
    private final MemoRepository memoRepository;
    private final MemoMediaRepository memoMediaRepository;
    @Override
    @Transactional
    public void register(String mediaSource, Long memoId) {
        final Media media = mediaRepository.findBySrc(mediaSource)
                .orElseThrow(() -> new NoSuchMediaException(ErrorCode.NO_SUCH_MEDIA));
        final Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoMediaException(ErrorCode.MEMO_NOT_EXIST));
        final List<MemoMedia> memoMedias = memoMediaRepository.findAllByMemo_Id(memoId);
        memoMediaRepository.save(MemoMedia.of(memo, media, memoMedias.size()));
    }
}
