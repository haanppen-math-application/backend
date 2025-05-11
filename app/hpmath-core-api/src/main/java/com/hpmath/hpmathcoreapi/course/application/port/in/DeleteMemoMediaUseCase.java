package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.DeleteMemoMediaCommand;

public interface DeleteMemoMediaUseCase {
    void delete(final DeleteMemoMediaCommand deleteMemoMediaCommand);
}
