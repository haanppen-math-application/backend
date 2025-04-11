package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoView;
import org.springframework.data.domain.Page;

public interface QueryMemosPort {
    Page<MemoView> loadMemos(final MemoQueryCommand memoQueryCommand);
}
