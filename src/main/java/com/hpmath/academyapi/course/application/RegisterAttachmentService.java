package com.hpmath.academyapi.course.application;

import com.hpmath.academyapi.course.application.dto.RegisterAttachmentCommand;
import com.hpmath.academyapi.course.application.port.in.RegisterAttachmentUseCase;
import com.hpmath.academyapi.course.application.port.out.RegisterMediaAttachmentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterAttachmentService implements RegisterAttachmentUseCase {
    private final RegisterMediaAttachmentPort registerMediaAttachmentPort;

    @Override
    public void register(final RegisterAttachmentCommand registerAttachmentCommand) {
        registerMediaAttachmentPort.register(registerAttachmentCommand.memoMediaId(),
                registerAttachmentCommand.mediaSrc());
    }
}
