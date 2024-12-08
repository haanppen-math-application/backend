package com.hanpyeon.academyapi.online.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineCourseRepository extends JpaRepository<OnlineCourse, Long> {
    List<OnlineCourse> findAllByTeacherId(final Long teacherId);

    @Query("SELECT o FROM OnlineCourse o JOIN OnlineStudent s ON o.id = s.course.id WHERE s.member.id = :studentId")
    List<OnlineCourse> findAllByStudentId(@Param("studentId") final Long studentId);

    @Query("SELECT oc from OnlineCourse oc "
            + "JOIN FETCH OnlineStudent os ON oc.id = os.course.id "
            + "JOIN FETCH Member m ON os.member.id = m.id "
            + "WHERE oc.id = :onlineCourseId")
    OnlineCourse findOnlineCourse(@Param("onlineCourseId") final Long onlineCourseId);

    @Query("SELECT oc FROM OnlineCourse oc LEFT JOIN FETCH oc.onlineCategory WHERE oc.id = :onlineCourseId")
    Optional<OnlineCourse> loadCourseAndCategoryByCourseId(@Param("onlineCourseId") final Long onlineCourseId);

    @Query("SELECT oc FROM OnlineCourse oc JOIN FETCH oc.teacher LEFT JOIN FETCH oc.videos WHERE oc.id = :onlineCourseId")
    Optional<OnlineCourse> loadCourseAndVideosByCourseId(@Param("onlineCourseId") final Long onlineCourseId);
}
