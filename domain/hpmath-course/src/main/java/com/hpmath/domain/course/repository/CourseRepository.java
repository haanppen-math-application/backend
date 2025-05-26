package com.hpmath.domain.course.repository;

import com.hpmath.domain.course.entity.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM COURSE c LEFT JOIN FETCH c.students WHERE c.teacherId = :teacherId")
    List<Course> findAllWithStudentsByTeacherId(@Param("teacherId") final Long teacherId);

    @Query(value = "SELECT c FROM COURSE c LEFT JOIN FETCH c.students")
    List<Course> findAllWithStudents();

    @Modifying
    @Query("UPDATE COURSE c SET c.teacherId = NULL WHERE c.teacherId IN :teacherIds")
    void updateTeacherToNullIn(@Param("teacherIds") final List<Long> teacherIds);

    @Query("SELECT c FROM COURSE c LEFT JOIN FETCH c.students WHERE c.id = :courseId")
    Optional<Course> findWithStudents(@Param("courseId") final Long courseId);

    @Query("SELECT c FROM COURSE c LEFT JOIN FETCH c.memos WHERE c.id = :courseId")
    Optional<Course> findWithMemos(@Param("courseId") final Long courseId);

    @Query("SELECT c FROM COURSE c JOIN FETCH c.students cs WHERE cs.studentId = :studentId")
    List<Course> findAllByStudentId(@Param("studentId") final Long studentId);
}
