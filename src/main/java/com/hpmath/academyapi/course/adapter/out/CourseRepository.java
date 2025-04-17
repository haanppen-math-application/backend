package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.course.entity.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByTeacherId(final Long teacherId);
}
