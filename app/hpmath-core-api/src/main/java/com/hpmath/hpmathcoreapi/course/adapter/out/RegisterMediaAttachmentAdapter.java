package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.client.media.MediaClient;
import com.hpmath.client.media.MediaClient.MediaInfo;
import com.hpmath.hpmathcoreapi.course.application.exception.MemoMediaException;
import com.hpmath.hpmathcoreapi.course.application.port.out.RegisterMediaAttachmentPort;
import com.hpmath.hpmathcoreapi.course.entity.MemoMediaAttachment;
import com.hpmath.hpmathcoreapi.course.entity.MemoMedia;
import com.hpmath.hpmathcore.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterMediaAttachmentAdapter implements RegisterMediaAttachmentPort {
    private final MediaAttachmentRepository mediaAttachmentRepository;
    private final MemoMediaRepository memoMediaRepository;
    private final MediaClient mediaClient;

    @Override
    @Transactional
    public void register(Long memoMediaId, String attachmentMediaId) {
        final MemoMedia targetMemoMedia = memoMediaRepository.findById(memoMediaId)
                .orElseThrow(() -> new MemoMediaException("해당 메모를 찾을 수 없음 : " + memoMediaId, ErrorCode.MEMO_NOT_EXIST));
        final MediaInfo mediaInfo = mediaClient.getFileInfo(attachmentMediaId);
        final MemoMediaAttachment memoMediaAttachment = MemoMediaAttachment.of(targetMemoMedia, mediaInfo.mediaSrc());
        mediaAttachmentRepository.save(memoMediaAttachment);
    }
}
