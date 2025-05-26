package com.hpmath.domain.course.repository;

import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.entity.Memo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemoRepositoryTest {
    @Autowired
    MemoRepository memoRepository;
    @Autowired
    CourseRepository courseRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void 메모_미디어_없어도_조회_된다() {
        final Course course = courseRepository.save(Course.of("test", 1L, List.of(1L)));
        course.addMemo(LocalDate.of(2020, 11, 21), "test", "tset");
        final Long courseId = course.getId();

        em.flush();
        em.clear();

        final Course target = courseRepository.findById(courseId).get();
        final Long memoId = target.getMemos().get(0).getId();

        Assertions.assertTrue(memoRepository.findWithCourseAndMediasByMemoId(memoId).isPresent());
    }

    @Test
    void course_없으면_조회_안된다() {
        final Long memoId = memoRepository.save(Memo.of(null, LocalDate.of(2020, 11, 21), "test", "tset")).getId();
        Assertions.assertTrue(memoRepository.findWithCourseAndMediasByMemoId(memoId).isEmpty());
    }
}