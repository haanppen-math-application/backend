package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.MemoRegisterCommand;

public interface MemoRegisterUseCase {
    Long register(final MemoRegisterCommand memoRegisterCommand);
}
