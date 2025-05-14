package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.Media;
import java.util.List;

public interface LoadMediasPort {
    List<Media> loadMedias(final List<String> mediaIds);
}
