package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.controller.Responses.AttachmentViewResponse;
import java.util.List;

public interface QueryMemoMediaAttachmentPort {
    List<AttachmentViewResponse> query(final Long memoMediaId);
}
