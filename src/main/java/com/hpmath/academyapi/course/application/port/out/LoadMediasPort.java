package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.Media;
import java.util.List;

public interface LoadMediasPort {
    List<Media> loadMedias(final List<String> mediaIds);
}
