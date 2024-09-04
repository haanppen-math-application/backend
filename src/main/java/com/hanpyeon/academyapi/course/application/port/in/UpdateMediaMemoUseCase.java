package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;

public interface UpdateMediaMemoUseCase {
    void addMedia(final UpdateMediaMemoCommand updateMediaMemoCommand);
}
