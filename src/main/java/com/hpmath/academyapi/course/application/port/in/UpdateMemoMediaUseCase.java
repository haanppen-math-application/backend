package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.UpdateMediaMemoCommand;

public interface UpdateMemoMediaUseCase {
    void updateMediaMemo(final UpdateMediaMemoCommand updateMediaMemoCommand);
}
