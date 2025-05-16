package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.application.dto.UpdateMediaMemoCommand;

public interface UpdateMemoMediaUseCase {
    void updateMediaMemo(final UpdateMediaMemoCommand updateMediaMemoCommand);
}
