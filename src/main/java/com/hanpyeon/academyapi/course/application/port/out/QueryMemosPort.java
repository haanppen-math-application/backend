package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoQueryRequest;
import com.hanpyeon.academyapi.course.application.dto.MemoView;
import org.springframework.data.domain.Slice;

public interface QueryMemosPort {
    Slice<MemoView> loadMemos(final MemoQueryCommand memoQueryCommand);
}
