package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.ModifyMemoTextCommand;

public interface ModifyMemoTextUseCase {
    void modify(final ModifyMemoTextCommand command);
}
