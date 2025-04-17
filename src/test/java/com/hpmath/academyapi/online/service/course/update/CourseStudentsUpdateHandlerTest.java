package com.hpmath.academyapi.online.service.course.update;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.account.repository.MemberRepository;
import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.dao.OnlineCourseRepository;
import com.hpmath.academyapi.online.dao.OnlineStudent;
import com.hpmath.academyapi.online.dao.OnlineStudentRepository;
import com.hpmath.academyapi.online.dto.OnlineCourseStudentUpdateCommand;
import com.hpmath.academyapi.security.Role;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class CourseStudentsUpdateHandlerTest {

    @Autowired
    private OnlineCourseStudentsUpdateHandler courseStudentsUpdateHandler;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private OnlineStudentRepository onlineStudentRepository;

    @Test
    @Transactional
    void testClear() {
        List.of(
                        Member.builder()
                                .name("test")
                                .encryptedPassword("test")
                                .role(Role.STUDENT)
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
        ).stream()
                .forEach(memberRepository::save);
        final OnlineCourse onlineCourse = new OnlineCourse(null, "test");
        onlineCourseRepository.save(onlineCourse);
        onlineStudentRepository.saveAll(List.of(
                        new OnlineStudent(onlineCourse, null),
                        new OnlineStudent(onlineCourse, null),
                        new OnlineStudent(onlineCourse, null),
                        new OnlineStudent(onlineCourse, null)
                )
        );

        Assertions.assertEquals(onlineStudentRepository.findAll().size(), 4);

        courseStudentsUpdateHandler.update(onlineCourse,
                new OnlineCourseStudentUpdateCommand(1L, 1L, Collections.emptyList()));

        Assertions.assertEquals(onlineStudentRepository.count(), 0);
    }

}