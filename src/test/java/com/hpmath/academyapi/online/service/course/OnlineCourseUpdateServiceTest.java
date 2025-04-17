package com.hpmath.academyapi.online.service.course;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.account.repository.MemberRepository;
import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.dao.OnlineCourseRepository;
import com.hpmath.academyapi.online.dao.OnlineStudent;
import com.hpmath.academyapi.online.dao.OnlineStudentRepository;
import com.hpmath.academyapi.online.dto.DeleteOnlineCourseCommand;
import com.hpmath.academyapi.security.Role;
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
class OnlineCourseUpdateServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private OnlineStudentRepository onlineStudentRepository;
    @Autowired
    private OnlineCourseUpdateService onlineCourseUpdateService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void deleteTest() {
        final Member teacher = Member.builder()
                .name("test")
                .encryptedPassword("test")
                .role(Role.TEACHER)
                .build();
        memberRepository.save(teacher);
        final OnlineCourse onlineCourse1 = new OnlineCourse(teacher, "test");
        final OnlineCourse onlineCourse2 = new OnlineCourse(teacher, "test");
        onlineCourseRepository.saveAll(List.of(onlineCourse1, onlineCourse2));

        onlineStudentRepository.saveAll(
                List.of(new OnlineStudent(onlineCourse1, null), new OnlineStudent(onlineCourse1, null)));
        onlineStudentRepository.saveAll(List.of(new OnlineStudent(onlineCourse2, null)));

        entityManager.flush();

        onlineCourseUpdateService.deleteOnlineCourse(
                new DeleteOnlineCourseCommand(onlineCourse1.getId(), teacher.getId(), Role.TEACHER));
        Assertions.assertThat(onlineStudentRepository.findAll().size())
                .isEqualTo(1);
    }
}