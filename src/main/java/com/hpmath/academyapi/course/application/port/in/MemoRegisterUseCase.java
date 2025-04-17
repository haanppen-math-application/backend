package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.MemoRegisterCommand;

public interface MemoRegisterUseCase {
    Long register(final MemoRegisterCommand memoRegisterCommand);
}
