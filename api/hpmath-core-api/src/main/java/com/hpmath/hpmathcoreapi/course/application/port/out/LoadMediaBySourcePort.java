package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.Media;

public interface LoadMediaBySourcePort {
    Media loadMediaBySource(final String source);
}
