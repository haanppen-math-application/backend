package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.DeleteMemoMediaCommand;

public interface DeleteMemoMediaUseCase {
    void delete(final DeleteMemoMediaCommand deleteMemoMediaCommand);
}
