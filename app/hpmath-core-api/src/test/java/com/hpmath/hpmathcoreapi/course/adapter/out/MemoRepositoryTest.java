package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.HpmathCoreApiApplication;
import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.course.entity.Course;
import com.hpmath.hpmathcoreapi.course.entity.CourseStudent;
import com.hpmath.hpmathcoreapi.course.entity.Memo;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = HpmathCoreApiApplication.class)
@ActiveProfiles("test")
class MemoRepositoryTest {
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
    @Transactional
    void 선생님_번호를_통한_수업_조회_테스트() {
        init();

        final LocalDate today = LocalDate.now();
        final LocalDate startDayOfMonth = today.withDayOfMonth(1);
        final LocalDate endDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        student = memberRepository.findMemberByIdAndRemovedIsFalse(student.getId())
                .orElseThrow();
        course = courseRepository.findById(course.getId())
                .orElseThrow();
        final Long studentId = student.getId();
        memoRepository.save(new Memo(course, today, "test", "test"));
        memoRepository.save(new Memo(course, today, "test", "test"));
        memoRepository.save(new Memo(course, today, "test", "test"));
        memoRepository.save(new Memo(course, LocalDate.MAX, "test", "test"));
        memoRepository.save(new Memo(course, LocalDate.MAX, "test", "test"));

        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(memoRepository.findAllByMonthAndStudentId(
                        startDayOfMonth, endDayOfMonth, studentId).size(),
                3);
    }
}