package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.application.dto.DeleteMemoMediaCommand;

public interface DeleteMemoMediaUseCase {
    void delete(final DeleteMemoMediaCommand deleteMemoMediaCommand);
}
