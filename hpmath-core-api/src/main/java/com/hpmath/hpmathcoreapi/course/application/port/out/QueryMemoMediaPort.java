package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.controller.Responses.MediaViewResponse;
import java.util.List;

public interface QueryMemoMediaPort {
    List<MediaViewResponse> queryMedias(final Long memoId);
}
