package com.hpmath.academyapi.online.service.lesson;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.account.repository.MemberRepository;
import com.hpmath.academyapi.online.dao.OnlineCategory;
import com.hpmath.academyapi.online.dao.OnlineCategoryRepository;
import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.dao.OnlineCourseRepository;
import com.hpmath.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.academyapi.security.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
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