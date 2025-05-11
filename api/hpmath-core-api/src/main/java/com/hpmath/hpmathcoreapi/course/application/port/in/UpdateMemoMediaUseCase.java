package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.UpdateMediaMemoCommand;

public interface UpdateMemoMediaUseCase {
    void updateMediaMemo(final UpdateMediaMemoCommand updateMediaMemoCommand);
}
