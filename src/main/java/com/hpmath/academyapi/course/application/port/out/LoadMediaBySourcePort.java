package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.Media;

public interface LoadMediaBySourcePort {
    Media loadMediaBySource(final String source);
}
