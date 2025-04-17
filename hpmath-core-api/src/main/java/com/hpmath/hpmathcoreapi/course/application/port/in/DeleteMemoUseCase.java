package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.DeleteMemoCommand;

public interface DeleteMemoUseCase {

    void delete(final DeleteMemoCommand deleteMemoCommand);
}
