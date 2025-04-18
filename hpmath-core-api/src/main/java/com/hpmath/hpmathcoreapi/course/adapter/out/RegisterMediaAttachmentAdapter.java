package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.application.exception.MemoMediaException;
import com.hpmath.hpmathcoreapi.course.application.port.out.RegisterMediaAttachmentPort;
import com.hpmath.hpmathcoreapi.course.entity.MemoMediaAttachment;
import com.hpmath.hpmathcoreapi.course.entity.MemoMedia;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcoreapi.media.entity.Media;
import com.hpmath.hpmathcoreapi.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterMediaAttachmentAdapter implements RegisterMediaAttachmentPort {
    private final MediaAttachmentRepository mediaAttachmentRepository;
    private final MemoMediaRepository memoMediaRepository;
    private final MediaRepository mediaRepository;

    @Override
    @Transactional
    public void register(Long memoMediaId, String attachmentMediaId) {
        final MemoMedia targetMemoMedia = memoMediaRepository.findById(memoMediaId)
                .orElseThrow(() -> new MemoMediaException("해당 메모를 찾을 수 없음 : " + memoMediaId, ErrorCode.MEMO_NOT_EXIST));
        final Media attachment = mediaRepository.findBySrc(attachmentMediaId)
                .orElseThrow(() -> new MemoMediaException("해당 미디어 파일을 찾을 수 없음" + attachmentMediaId, ErrorCode.NO_SUCH_MEDIA));
        final MemoMediaAttachment memoMediaAttachment = MemoMediaAttachment.of(targetMemoMedia, attachment);
        mediaAttachmentRepository.save(memoMediaAttachment);
    }
}
