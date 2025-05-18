package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.RegisterAttachmentCommand;

public interface RegisterAttachmentUseCase {
    void register(final RegisterAttachmentCommand registerAttachmentCommand);
}
