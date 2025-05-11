package com.hpmath.hpmathcoreapi.online.dao;

import com.hpmath.HpmathCoreApiApplication;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = HpmathCoreApiApplication.class)
@ActiveProfiles("test")
class OnlineStudentRepositoryTest {

    @Autowired
    private OnlineStudentRepository onlineStudentRepository;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void deleteQueryTest() {
        final OnlineCourse onlineCourse = new OnlineCourse(null, "test");
        final List<OnlineStudent> onlineStudents = List.of(
                new OnlineStudent(onlineCourse, null),
                new OnlineStudent(onlineCourse, null),
                new OnlineStudent(onlineCourse, null)
        );
        onlineCourseRepository.save(onlineCourse);
        onlineStudentRepository.saveAll(onlineStudents);

        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(onlineStudentRepository.count(), 3);
        onlineStudentRepository.removeAllByOnlineCourseId(onlineCourse.getId());
        Assertions.assertEquals(onlineStudentRepository.count(), 0);
        Assertions.assertEquals(onlineCourseRepository.count(), 1);
    }
}