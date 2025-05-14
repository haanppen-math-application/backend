package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.entity.Course;
import com.hpmath.hpmathcoreapi.course.entity.CourseStudent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {
    List<CourseStudent> findCourseStudentByStudentId(final Long memberId);
    void deleteCourseStudentByCourseEntityId(final Long courseId);
}
