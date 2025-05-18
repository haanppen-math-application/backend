package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.entity.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByTeacherId(final Long teacherId);

    @Modifying
    @Query("UPDATE COURSE c SET c.teacherId = NULL WHERE c.teacherId IN :teacherIds")
    void updateTeacherToNullIn(@Param("teacherIds") final List<Long> teacherIds);
}
