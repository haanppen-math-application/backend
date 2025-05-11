package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.MemoMedia;
import java.util.List;

public interface LoadMemoMediaPort {
    List<MemoMedia> loadMedia(final Long memoId);
}
