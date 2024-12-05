package com.hanpyeon.academyapi.online;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.online.dao.OnlineCategoryRepository;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineStudent;
import com.hanpyeon.academyapi.online.dao.OnlineStudentRepository;
import com.hanpyeon.academyapi.online.dto.AddOnlineCourseCommand;
import com.hanpyeon.academyapi.online.dto.DeleteOnlineCourseCommand;
import com.hanpyeon.academyapi.online.service.OnlineCourseService;
import com.hanpyeon.academyapi.security.Role;
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
class OnlineCourseServiceTest {

    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OnlineStudentRepository onlineStudentRepository;
    @Autowired
    private OnlineCourseService onlineCourseService;

    @Test
    @Transactional
    void 온라인_강의_등록_테스트() {
        this.memberRepository.saveAll(
                List.of(
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
                )
        );
        final AddOnlineCourseCommand addOnlineCourseCommand = new AddOnlineCourseCommand(
                1L,
                Role.TEACHER,
                "test",
                List.of(2L, 3L),
                1L
        );

        onlineCourseService.addOnlineCourse(addOnlineCourseCommand);

        org.junit.jupiter.api.Assertions.assertAll(() -> {
            Assertions.assertThat(onlineCourseRepository.findAll().size()).isEqualTo(1);
            Assertions.assertThat(onlineCourseRepository.findAll().get(0).getOnlineStudents().size()).isEqualTo(2);
            Assertions.assertThat(onlineStudentRepository.findAll().get(0).getCourse()).isNotNull();
        });
    }
}