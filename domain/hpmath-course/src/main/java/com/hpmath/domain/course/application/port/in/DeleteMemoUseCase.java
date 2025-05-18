package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.DeleteMemoCommand;

public interface DeleteMemoUseCase {

    void delete(final DeleteMemoCommand deleteMemoCommand);
}
