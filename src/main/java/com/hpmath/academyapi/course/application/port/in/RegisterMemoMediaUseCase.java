package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.RegisterMemoMediaCommand;

public interface RegisterMemoMediaUseCase {
    void register(final RegisterMemoMediaCommand command);
}
