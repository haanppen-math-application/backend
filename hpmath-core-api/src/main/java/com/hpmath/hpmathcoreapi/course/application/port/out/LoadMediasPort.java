package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.Media;
import java.util.List;

public interface LoadMediasPort {
    List<Media> loadMedias(final List<String> mediaIds);
}
