package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryRequest;
import com.hanpyeon.academyapi.course.application.dto.MemoView;
import org.springframework.data.domain.Slice;

public interface LoadMemoQuery {
    Slice<MemoView> loadMemos(final MemoQueryRequest memoQueryCommand);
}
