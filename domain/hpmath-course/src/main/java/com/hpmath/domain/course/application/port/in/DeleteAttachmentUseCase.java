package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.DeleteAttachmentCommand;

public interface DeleteAttachmentUseCase {
    void delete(final DeleteAttachmentCommand deleteAttachmentCommand);
}
