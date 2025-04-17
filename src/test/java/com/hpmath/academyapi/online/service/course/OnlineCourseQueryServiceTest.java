package com.hpmath.academyapi.online.service.course;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.account.repository.MemberRepository;
import com.hpmath.academyapi.online.dao.OnlineCategory;
import com.hpmath.academyapi.online.dao.OnlineCategoryRepository;
import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.dao.OnlineCourseRepository;
import com.hpmath.academyapi.online.dao.OnlineStudent;
import com.hpmath.academyapi.online.dao.OnlineStudentRepository;
import com.hpmath.academyapi.online.dto.OnlineCoursePreview;
import com.hpmath.academyapi.security.Role;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class OnlineCourseQueryServiceTest {

    @Autowired
    private OnlineCourseQueryService queryOnlineCourseService;
    @Autowired
    private OnlineCategoryRepository onlineCategoryRepository;
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
        final OnlineCategory onlineCategory = new OnlineCategory("test");
        onlineCategoryRepository.save(onlineCategory);
        onlineCourse.setOnlineCategory(onlineCategory);
        onlineCourseRepository.save(onlineCourse);
        onlineStudentRepository.saveAll(
                List.of(new OnlineStudent(onlineCourse, null), new OnlineStudent(onlineCourse, null)));

        final OnlineCoursePreview onlineCoursePreview = queryOnlineCourseService.queryAll().get(0);
        Assertions.assertAll(() -> {
            Assertions.assertEquals(onlineCoursePreview.courseName(), "test");
            Assertions.assertEquals(onlineCoursePreview.teacherPreview().teacherName(), "test");
        });
    }
}