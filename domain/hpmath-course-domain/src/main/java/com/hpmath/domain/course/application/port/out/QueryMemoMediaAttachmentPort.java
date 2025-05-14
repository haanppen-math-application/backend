package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.application.dto.Responses.AttachmentViewResponse;
import java.util.List;

public interface QueryMemoMediaAttachmentPort {
    List<AttachmentViewResponse> query(final Long memoMediaId);
}
