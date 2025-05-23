package com.hpmath.domain.online.service.lesson;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.online.dao.OnlineCategory;
import com.hpmath.domain.online.dao.OnlineCategoryRepository;
import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dao.OnlineCourseRepository;
import com.hpmath.domain.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.common.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OnlineLessonServiceTest {
    private static final String newTitle = "testTitle";
    private static final String newRange = "testRange";
    private static final String newDescribe = "testDescribe";

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private OnlineCategoryRepository onlineCategoryRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OnlineLessonUpdateService onlineLessonService;

//    @Test
//    @Transactional
    void testUpdate() {
        final Member member = Member.builder()
                .name("test")
                .encryptedPassword("test")
                .role(Role.TEACHER)
                .build();
        final OnlineCourse onlineCourse = new OnlineCourse(member, "test");
        final OnlineCategory onlineCategory = new OnlineCategory("test");

        final Long memberId = memberRepository.save(member).getId();
        final Long courseId = onlineCourseRepository.save(onlineCourse).getId();
        final Long categoryId = onlineCategoryRepository.save(onlineCategory).getId();

        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        final UpdateOnlineLessonInfoCommand command = new UpdateOnlineLessonInfoCommand(
                courseId,
                newTitle,
                newRange,
                newDescribe,
                categoryId,
                memberId,
                Role.MANAGER,
        "test");

        onlineLessonService.updateLessonInfo(command);

        entityManager.flush();
        entityManager.clear();

        final OnlineCourse updatedCourse = onlineCourseRepository.loadCourseAndCategoryByCourseId(courseId).orElseThrow();
        Assertions.assertAll(() -> {
            Assertions.assertEquals(updatedCourse.getOnlineCategory().getId(), categoryId);
            Assertions.assertEquals(updatedCourse.getCourseRange(), newRange);
            Assertions.assertEquals(updatedCourse.getCourseTitle(), newTitle);
            Assertions.assertEquals(updatedCourse.getCourseContent(), newDescribe);
        });
    }
}