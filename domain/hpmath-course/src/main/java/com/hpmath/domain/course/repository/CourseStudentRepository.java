package com.hpmath.domain.course.repository;

import com.hpmath.domain.course.entity.CourseStudent;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {
    List<CourseStudent> findCourseStudentByStudentId(final Long memberId);
    void deleteCourseStudentByCourseEntityId(final Long courseId);

    @Query("DELETE FROM CourseStudent cs WHERE cs.studentId IN :studentIds")
    @Modifying
    Integer deleteCourseStudentsByStudentIdIn(@Param("studentIds") Collection<Long> studentIds);
}
