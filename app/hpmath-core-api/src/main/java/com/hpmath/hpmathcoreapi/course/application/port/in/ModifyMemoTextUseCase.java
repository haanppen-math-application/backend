package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.ModifyMemoTextCommand;

public interface ModifyMemoTextUseCase {
    void modify(final ModifyMemoTextCommand command);
}
