package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.entity.Course;
import com.hpmath.hpmathcoreapi.course.entity.CourseStudent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Slf4j
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CourseStudentRepository courseStudentRepository;

    @Test
    void 첫_등록_테스트() {
        final Course course = Course.of("test", 1L, List.of(1L, 2L, 3L));

        courseRepository.save(course);

        final Long courseId = course.getId();

        entityManager.flush();
        entityManager.clear();

        final Course savedCourse = courseRepository.findById(courseId).get();

        savedCourse.getStudents().stream()
                .map(CourseStudent::toString)
                        .forEach(log::info);
        Assertions.assertEquals(3, savedCourse.getCourseStudents().size());
    }

    @Test
    void 학생_추가_테스트() {
        final Course course = Course.of("test", 1L, List.of(1L, 2L, 3L));

        courseRepository.save(course);

        final Long courseId = course.getId();

        entityManager.flush();
        entityManager.clear();

        Course savedCourse = courseRepository.findById(courseId).get();

        savedCourse.getStudents().add(CourseStudent.of(4L, savedCourse));

        entityManager.flush();
        entityManager.clear();

        savedCourse = courseRepository.findById(courseId).get();

        Assertions.assertEquals(4, savedCourse.getCourseStudents().size());
    }

    @Test
    void 학생_삭제_후_추가_테스트() {
        final Course course = Course.of("test", 1L, List.of(1L, 2L, 3L));

        courseRepository.save(course);

        final Long courseId = course.getId();

        entityManager.flush();
        entityManager.clear();

        Course savedCourse = courseRepository.findById(courseId).get();

        savedCourse.getStudents().clear();
        savedCourse.getStudents().add(CourseStudent.of(4L, savedCourse));

        entityManager.flush();
        entityManager.clear();

        savedCourse = courseRepository.findById(courseId).get();

        Assertions.assertEquals(1, savedCourse.getCourseStudents().size());
        Assertions.assertEquals(1, courseStudentRepository.findAll().size());
    }

    @Test
    void 수업_삭제시_학생도_모두_삭제() {
        final Course course = Course.of("test", 1L, List.of(1L, 2L, 3L));

        courseRepository.save(course);

        final Long courseId = course.getId();

        entityManager.flush();
        entityManager.clear();

        courseRepository.deleteById(courseId);

        entityManager.flush();
        entityManager.clear();

        courseStudentRepository.findAll().stream()
                .forEach(courseStudent -> log.info(courseStudent.toString()));
        Assertions.assertEquals(0, courseStudentRepository.findAll().size());
    }
}