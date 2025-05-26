package com.hpmath.domain.course.entity;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CourseTest {
    @Test
    void 학생_중복_테스트() {
        final Long studentId = 2L;

        Course course = Course.of("testCourse", 1L, List.of(studentId));
        course.addStudents(List.of(studentId));

        Assertions.assertEquals(1L, course.getStudents().size());
    }
}