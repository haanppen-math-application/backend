package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.application.exception.MemoMediaException;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadAttachmentOwnedMemberIdPort;
import com.hpmath.hpmathcore.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoadAttachmentOwnedMemberIdAdapter implements LoadAttachmentOwnedMemberIdPort {
    private final MediaAttachmentRepository mediaAttachmentRepository;

    @Override
    @Transactional(readOnly = true)
    public Long findOwnerId(Long attachmentId) {
        return mediaAttachmentRepository.findCourseOwnerMemberOnThisMemoAttachment(attachmentId)
                .orElseThrow(() -> new MemoMediaException("해당 코스의 소유자를 찾을 수 없음", ErrorCode.CANNOT_FIND_ATTACHMENT));
    }
}
