package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.dto.DeleteAttachmentCommand;
import com.hpmath.domain.course.dto.RegisterAttachmentCommand;
import com.hpmath.domain.course.entity.MemoMedia;
import com.hpmath.domain.course.entity.MemoMediaAttachment;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.exception.MemoMediaException;
import com.hpmath.domain.course.repository.MediaAttachmentRepository;
import com.hpmath.domain.course.repository.MemoMediaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class CourseMemoMediaAttachmentService {
    private final MediaAttachmentRepository mediaAttachmentRepository;
    private final MemoMediaRepository memoMediaRepository;

    public void register(@Valid final RegisterAttachmentCommand command) {
        final MemoMedia targetMemoMedia = memoMediaRepository.findById(command.memoMediaId())
                .orElseThrow(() -> new MemoMediaException("해당 메모를 찾을 수 없음 : " + command.memoMediaId(), ErrorCode.MEMO_NOT_EXIST));
        final MemoMediaAttachment memoMediaAttachment = MemoMediaAttachment.of(targetMemoMedia, command.mediaSrc());
        mediaAttachmentRepository.save(memoMediaAttachment);
    }

    public void delete(@Valid DeleteAttachmentCommand command) {
        validateAuthority(command);
        mediaAttachmentRepository.deleteById(command.getTargetAttachmentId());
    }

    private void validateAuthority(final DeleteAttachmentCommand command) {
        if (validateAttachmentOwner(command.getTargetAttachmentId(), command.getRequestMemberId())) {
            return;
        }
        if (command.getRole().equals(Role.ADMIN) || command.getRole().equals(Role.MANAGER)) {
            return;
        }
        throw new CourseException("첨부파일을 지울 수 있는 권한이 없습니다.", ErrorCode.CANNOT_FIND_ATTACHMENT);
    }

    private boolean validateAttachmentOwner(final Long targetAttachmentId, final Long requestMemberId) {
        final Long ownerId = mediaAttachmentRepository.findCourseOwnerMemberOnThisMemoAttachment(targetAttachmentId)
                .orElse(null);

        return ownerId.equals(requestMemberId);
    }
}
