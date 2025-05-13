package com.hpmath.domain.online.service.course;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.online.dao.OnlineCategory;
import com.hpmath.domain.online.dao.OnlineCategoryRepository;
import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dao.OnlineCourseRepository;
import com.hpmath.domain.online.dao.OnlineStudent;
import com.hpmath.domain.online.dao.OnlineStudentRepository;
import com.hpmath.domain.online.dto.OnlineCoursePreview;
import com.hpmath.hpmathcore.Role;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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