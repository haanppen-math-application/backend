package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.MemoMedia;
import java.util.List;

public interface LoadMemoMediaPort {
    List<MemoMedia> loadMedia(final Long memoId);
}
