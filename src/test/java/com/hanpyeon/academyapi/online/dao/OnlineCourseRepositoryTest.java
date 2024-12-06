package com.hanpyeon.academyapi.online.dao;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
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
    private MemberRepository memberRepository;
    @Autowired
    private OnlineStudentRepository onlineStudentRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void studentQueryTest() {
        final List<Member> members = List.of(
                Member.builder()
                        .name("test")
                        .encryptedPassword("test")
                        .role(Role.TEACHER)
                        .build(),
                Member.builder()
                        .name("test")
                        .encryptedPassword("test")
                        .role(Role.STUDENT)
                        .build(),
                Member.builder()
                        .name("test")
                        .encryptedPassword("test")
                        .role(Role.STUDENT)
                        .build()
        );

        this.memberRepository.saveAll(members);

        final OnlineCourse onlineCourse = new OnlineCourse(null, "test");
        final OnlineStudent onlineStudent1 = new OnlineStudent(onlineCourse, members.get(0));
        final OnlineStudent onlineStudent2 = new OnlineStudent(onlineCourse, members.get(1));
        final OnlineStudent onlineStudent3 = new OnlineStudent(onlineCourse, members.get(2));
        onlineCourseRepository.save(onlineCourse);
        onlineStudentRepository.saveAll(List.of(
                        onlineStudent1,
                        onlineStudent2,
                        onlineStudent3
                )
        );
        entityManager.flush();

        Assertions.assertEquals(onlineCourseRepository.findAllByStudentId(members.get(0).getId()).size(), 1);
    }

}