package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.controller.Responses.AttachmentViewResponse;
import java.util.List;

public interface QueryMemoMediaAttachmentPort {
    List<AttachmentViewResponse> query(final Long memoMediaId);
}
