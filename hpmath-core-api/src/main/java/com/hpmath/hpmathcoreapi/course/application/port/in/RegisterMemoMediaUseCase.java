package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.RegisterMemoMediaCommand;

public interface RegisterMemoMediaUseCase {
    void register(final RegisterMemoMediaCommand command);
}
