package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AddCourseStudentAdapter.class)
class AddCourseStudentAdapterTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseStudentRepository courseStudentRepository;

    @Autowired
    AddCourseStudentAdapter adapter;

    @BeforeEach
    void init() {
        memberRepository.save(Member.builder()
                .name("student")
                .grade(11)
                .phoneNumber("test")
                .password("weqrew")
                .registeredDate(LocalDateTime.now())
                .role(Role.STUDENT)
                .build());
        memberRepository.save(Member.builder()
                .name("student")
                .grade(11)
                .phoneNumber("test1")
                .password("weqrew")
                .registeredDate(LocalDateTime.now())
                .role(Role.STUDENT)
                .build());
        Member teacher = memberRepository.save(Member.builder()
                .name("teacher")
                .grade(11)
                .phoneNumber("teacher1")
                .password("weqrew")
                .registeredDate(LocalDateTime.now())
                .role(Role.TEACHER)
                .build());
        courseRepository.save(new Course("testCourse", teacher));
    }

    @Test
    void Course_Student_추가_테스트() {
        adapter.addToCourse(1l, List.of(1l, 2l));
        assertThat(courseStudentRepository.findAll().size())
                .isEqualTo(2);
    }
}