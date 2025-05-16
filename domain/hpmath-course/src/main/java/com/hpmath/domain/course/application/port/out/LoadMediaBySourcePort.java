package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.Media;

public interface LoadMediaBySourcePort {
    Media loadMediaBySource(final String source);
}
