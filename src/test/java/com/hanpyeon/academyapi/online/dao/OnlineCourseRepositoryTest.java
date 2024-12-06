package com.hanpyeon.academyapi.online.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class OnlineCourseRepositoryTest {

    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private OnlineStudentRepository onlineStudentRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void studentQueryTest() {
        final OnlineCourse onlineCourse = new OnlineCourse(null, "test");
        final OnlineStudent onlineStudent1 = new OnlineStudent(onlineCourse, null);
        final OnlineStudent onlineStudent2 = new OnlineStudent(onlineCourse, null);
        final OnlineStudent onlineStudent3 = new OnlineStudent(onlineCourse, null);
        onlineCourseRepository.save(onlineCourse);
        onlineStudentRepository.saveAll(List.of(
                        onlineStudent1,
                        onlineStudent2,
                        onlineStudent3
                )
        );
        entityManager.flush();

        Assertions.assertEquals(onlineCourseRepository.findAllByStudentId(onlineStudent1.getId()).size(), 1);
    }

}