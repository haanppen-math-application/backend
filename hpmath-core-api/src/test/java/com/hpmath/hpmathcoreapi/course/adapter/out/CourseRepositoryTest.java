package com.hpmath.hpmathcoreapi.course.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;

import com.hpmath.HpmathCoreApiApplication;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.course.entity.Course;
import com.hpmath.hpmathcoreapi.course.entity.CourseStudent;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = HpmathCoreApiApplication.class)
class CourseRepositoryTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CourseStudentRepository courseStudentRepository;
    @Autowired
    MemoRepository memoRepository;

    private Member student = Member.builder()
            .name("student")
            .grade(11)
            .phoneNumber("test")
            .encryptedPassword("weqrew")
            .registeredDate(LocalDateTime.now())
            .role(Role.STUDENT)
            .build();
    private Member student2 = Member.builder()
            .name("student")
            .grade(11)
            .phoneNumber("test1")
            .encryptedPassword("weqrew")
            .registeredDate(LocalDateTime.now())
            .role(Role.STUDENT)
            .build();
    private Member teacher = Member.builder()
            .name("teacher")
            .grade(11)
            .phoneNumber("teacher1")
            .encryptedPassword("weqrew")
            .registeredDate(LocalDateTime.now())
            .role(Role.TEACHER)
            .build();

    private Course course = new Course("test", teacher);

    void init() {
        memberRepository.save(student);
        memberRepository.save(student2);
        memberRepository.save(teacher);
        courseRepository.save(course);
        courseStudentRepository.save(CourseStudent.addToCourse(student, course));
        courseStudentRepository.save(CourseStudent.addToCourse(student2, course));
    }

    @Test
    void 선생님_번호를_통한_수업_조회_테스트() {
        init();
        assertThat(courseRepository.findAll().size())
                .isEqualTo(1);
    }
}