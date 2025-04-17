package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.DeleteAttachmentCommand;

public interface DeleteAttachmentUseCase {
    void delete(final DeleteAttachmentCommand deleteAttachmentCommand);
}
