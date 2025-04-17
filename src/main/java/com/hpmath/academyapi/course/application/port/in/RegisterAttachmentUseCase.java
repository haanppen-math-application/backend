package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.RegisterAttachmentCommand;

public interface RegisterAttachmentUseCase {
    void register(final RegisterAttachmentCommand registerAttachmentCommand);
}
