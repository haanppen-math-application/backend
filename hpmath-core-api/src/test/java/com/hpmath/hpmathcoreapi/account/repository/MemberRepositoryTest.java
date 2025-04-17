package com.hpmath.hpmathcoreapi.account.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.security.Role;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void init() {
        memberRepository.save(Member.builder()
                .name("student")
                .grade(11)
                .phoneNumber("test")
                .encryptedPassword("weqrew")
                .registeredDate(LocalDateTime.now())
                .role(Role.STUDENT)
                .build());
        memberRepository.save(Member.builder()
                .name("student")
                .grade(11)
                .phoneNumber("test1")
                .encryptedPassword("weqrew")
                .registeredDate(LocalDateTime.now())
                .role(Role.TEACHER)
                .build());
    }

    @Test
    void 역할_조회_테스트() {
        assertThat(memberRepository.findMembersByRole(Role.STUDENT).size())
                .isEqualTo(1);
        assertThat(memberRepository.findMembersByRole(Role.TEACHER).size())
                .isEqualTo(1);
    }
}