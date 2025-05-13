package com.hpmath.domain.online.dao;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.hpmathcore.Role;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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

        members.stream()
                .forEach(memberRepository::save);

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

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void fetchTest() {
        final List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            members.add(Member.builder()
                    .name("test" + i)
                    .encryptedPassword("test")
                    .role(Role.TEACHER)
                    .build());
        }
        members.stream()
                .forEach(memberRepository::save);

        final OnlineCourse onlineCourse = new OnlineCourse(members.get(0), "test");
        onlineCourseRepository.save(onlineCourse);

        onlineStudentRepository.saveAll(members.stream()
                .map(member -> new OnlineStudent(onlineCourse, member))
                .toList());

        entityManager.flush();
        final Long courseId = onlineCourse.getId();
        entityManager.clear();

        final OnlineCourse targetCourse = onlineCourseRepository.findOnlineCourse(courseId);
        targetCourse.getOnlineStudents().stream()
                .map(onlineStudent -> onlineStudent.getMember())
                .collect(Collectors.toList());
    }

    @Test
    void findTest() {
        final List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            members.add(Member.builder()
                    .name("test" + i)
                    .encryptedPassword("test")
                    .role(Role.TEACHER)
                    .build());
        }
        members.stream()
                .forEach(memberRepository::save);

        final OnlineCourse onlineCourse = new OnlineCourse(members.get(0), "test");
        onlineCourseRepository.save(onlineCourse);

        onlineStudentRepository.saveAll(members.stream()
                .map(member -> new OnlineStudent(onlineCourse, member))
                .toList());

        entityManager.flush();
        final Long courseId = onlineCourse.getId();
        entityManager.clear();

        Assertions.assertNotNull(onlineCourseRepository.loadCourseAndCategoryByCourseId(courseId).orElseThrow());
    }
}