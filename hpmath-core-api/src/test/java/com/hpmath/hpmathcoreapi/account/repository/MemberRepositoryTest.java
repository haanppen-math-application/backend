package com.hpmath.hpmathcoreapi.account.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

import com.hpmath.HpmathCoreApiApplication;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.account.entity.Member;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = HpmathCoreApiApplication.class)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

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
    @Transactional
    void 역할_조회_테스트() {
        init();
        assertThat(memberRepository.findMembersByRole(Role.STUDENT).size())
                .isEqualTo(1);
        assertThat(memberRepository.findMembersByRole(Role.TEACHER).size())
                .isEqualTo(1);
    }
}