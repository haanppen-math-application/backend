package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.DeleteMemoCommand;

public interface DeleteMemoUseCase {

    void delete(final DeleteMemoCommand deleteMemoCommand);
}
