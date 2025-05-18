package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.ModifyMemoTextCommand;

public interface ModifyMemoTextUseCase {
    void modify(final ModifyMemoTextCommand command);
}
