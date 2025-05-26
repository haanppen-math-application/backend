package com.hpmath.domain.course.service;

import com.hpmath.domain.course.dto.MemoRegisterCommand;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.repository.CourseRepository;
import com.hpmath.domain.course.repository.MemoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CourseMemoServiceTest {
    @Autowired
    CourseMemoService courseMemoService;
    @Autowired
    CourseRepository courseRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    void 메모_추가() {
        final Long courseId = courseRepository.save(Course.of("test", 1L, List.of(1L))).getId();

        entityManager.flush();
        entityManager.clear();

        courseMemoService.register(new MemoRegisterCommand(1L, courseId, "title", "content", LocalDate.now()));

        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(1, courseRepository.findById(courseId).get().getMemos().size());
    }

    @Test
    @Transactional
    void 중복_날짜_메모_추가시_에러_발생() {
        final Long courseId = courseRepository.save(Course.of("test", 1L, List.of(1L))).getId();

        entityManager.flush();
        entityManager.clear();

        courseMemoService.register(new MemoRegisterCommand(1L, courseId, "title", "content", LocalDate.of(2020, 1, 1)));
        entityManager.flush();
        entityManager.clear();
        Assertions.assertThrows(CourseException.class, () -> courseMemoService.register(new MemoRegisterCommand(1L, courseId, "title", "content", LocalDate.of(2020, 1, 1))));
    }
}