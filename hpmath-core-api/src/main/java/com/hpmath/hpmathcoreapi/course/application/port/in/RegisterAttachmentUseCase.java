package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.RegisterAttachmentCommand;

public interface RegisterAttachmentUseCase {
    void register(final RegisterAttachmentCommand registerAttachmentCommand);
}
