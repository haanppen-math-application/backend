package com.hpmath.domain.course.application.port.out;

import java.util.List;

public interface DeleteStudentsPort {
    void delete(List<Long> studentIds);
}
