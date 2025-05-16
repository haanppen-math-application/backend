package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.application.dto.MemoRegisterCommand;

public interface MemoRegisterUseCase {
    Long register(final MemoRegisterCommand memoRegisterCommand);
}
