package com.hpmath.hpmathcoreapi.online.service.course;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourseRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineStudentRepository;
import com.hpmath.hpmathcoreapi.online.dto.AddOnlineCourseCommand;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class OnlineCourseRegisterServiceTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OnlineStudentRepository onlineStudentRepository;
    @Autowired
    private OnlineCourseRegisterService onlineCourseService;

    @Test
    @Transactional
    void 온라인_강의_등록_테스트() {
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
        entityManager.flush();
        entityManager.clear();

        final Long requestMemberId = members.get(0).getId();
        final Long target1 = members.get(1).getId();
        final Long target2 = members.get(2).getId();

        entityManager.flush();
        entityManager.clear();

        final AddOnlineCourseCommand addOnlineCourseCommand = new AddOnlineCourseCommand(
                requestMemberId,
                Role.TEACHER,
                "test",
                List.of(target1, target2),
                requestMemberId
        );

        onlineCourseService.addOnlineCourse(addOnlineCourseCommand);

        entityManager.flush();
        entityManager.clear();

        org.junit.jupiter.api.Assertions.assertAll(() -> {
            Assertions.assertThat(onlineCourseRepository.findAll().size()).isEqualTo(1);
            Assertions.assertThat(onlineCourseRepository.findAll().get(0).getOnlineStudents().size()).isEqualTo(2);
            Assertions.assertThat(onlineStudentRepository.findAll().get(0).getCourse()).isNotNull();
        });
    }
}