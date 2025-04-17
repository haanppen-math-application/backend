package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.DeleteAttachmentCommand;

public interface DeleteAttachmentUseCase {
    void delete(final DeleteAttachmentCommand deleteAttachmentCommand);
}
