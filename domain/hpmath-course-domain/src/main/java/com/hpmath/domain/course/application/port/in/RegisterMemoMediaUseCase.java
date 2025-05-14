package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.application.dto.RegisterMemoMediaCommand;

public interface RegisterMemoMediaUseCase {
    void register(final RegisterMemoMediaCommand command);
}
