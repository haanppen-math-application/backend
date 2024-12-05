package com.hanpyeon.academyapi.online.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineStudent;
import com.hanpyeon.academyapi.online.dao.OnlineStudentRepository;
import com.hanpyeon.academyapi.online.dto.OnlineCoursePreview;
import com.hanpyeon.academyapi.security.Role;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class QueryOnlineCourseServiceTest {

    @Autowired
    private QueryOnlineCourseService queryOnlineCourseService;

    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private OnlineStudentRepository onlineStudentRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Test
    void queryTest() {
        final Member teacher = Member.builder().role(Role.TEACHER)
                .encryptedPassword("test")
                .name("test")
                .build();
        memberRepository.save(teacher);
        final OnlineCourse onlineCourse = new OnlineCourse(teacher, "test");
        onlineCourseRepository.save(onlineCourse);
        onlineStudentRepository.saveAll(
                List.of(new OnlineStudent(onlineCourse, null), new OnlineStudent(onlineCourse, null)));

        final OnlineCoursePreview onlineCoursePreview = queryOnlineCourseService.queryAll().getFirst();
        Assertions.assertAll(() -> {
            Assertions.assertEquals(onlineCoursePreview.courseName(), "test");
            Assertions.assertEquals(onlineCoursePreview.teacherPreview().teacherName(), "test");
        });
    }
}