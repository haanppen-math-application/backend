package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.RegisterAttachmentCommand;

public interface RegisterAttachmentUseCase {
    void register(final RegisterAttachmentCommand registerAttachmentCommand);
}
