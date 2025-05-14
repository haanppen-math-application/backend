package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.application.dto.Responses.MediaViewResponse;
import java.util.List;

public interface QueryMemoMediaPort {
    List<MediaViewResponse> queryMedias(final Long memoId);
}
