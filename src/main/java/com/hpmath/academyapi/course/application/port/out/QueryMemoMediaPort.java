package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.controller.Responses.MediaViewResponse;
import java.util.List;

public interface QueryMemoMediaPort {
    List<MediaViewResponse> queryMedias(final Long memoId);
}
