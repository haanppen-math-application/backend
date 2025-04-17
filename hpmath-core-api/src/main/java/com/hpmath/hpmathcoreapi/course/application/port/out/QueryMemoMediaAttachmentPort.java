package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.controller.Responses.AttachmentViewResponse;
import java.util.List;

public interface QueryMemoMediaAttachmentPort {
    List<AttachmentViewResponse> query(final Long memoMediaId);
}
