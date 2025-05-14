package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.MemoMedia;
import java.util.List;

public interface LoadMemoMediaPort {
    List<MemoMedia> loadMedia(final Long memoId);
}
