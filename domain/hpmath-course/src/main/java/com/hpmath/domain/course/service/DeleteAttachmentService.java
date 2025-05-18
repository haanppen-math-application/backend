package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.repository.MediaAttachmentRepository;
import com.hpmath.domain.course.dto.DeleteAttachmentCommand;
import com.hpmath.domain.course.exception.CourseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAttachmentService {
    private final MediaAttachmentRepository mediaAttachmentRepository;

    @Transactional
    public void delete(DeleteAttachmentCommand command) {
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
