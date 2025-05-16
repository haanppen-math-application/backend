package com.hpmath.domain.member.service;

import com.hpmath.common.Role;
import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.AccountRemoveCommand;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {"""
spring.jpa.show-sql=true
"""})
class AccountRemoveServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AccountRemoveService accountRemoveService;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    void testDelete() {
        List<Long> ids = initTestData();
        accountRemoveService.removeAccount(new AccountRemoveCommand(ids.subList(0, 2)));

        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(2, memberRepository.findMembersByRole(Role.STUDENT).size());
    }

    @Test
    @Transactional
    void SOFT_DELETE_삭제() {
        List<Long> ids = initTestData();
        memberRepository.deleteById(ids.get(0));

        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(memberRepository.findMembersByRole(Role.STUDENT).size(), 3);
        Assertions.assertEquals(memberRepository.findAll().size(), 4);
    }

    @Test
    @Transactional
    void JPQL_삭제시_SOFT_DELETE_무시() {
        List<Long> ids = initTestData();
        memberRepository.removeTarget(ids.get(0));

        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(memberRepository.findMembersByRole(Role.STUDENT).size(), 3);
        Assertions.assertEquals(memberRepository.findAll().size(), 3);
    }

    List<Long> initTestData() {
        return List.of(
                memberRepository.save(
                        Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now())
                                .phoneNumber("01000000000").encryptedPassword("test").build()).getId(),
                memberRepository.save(
                        Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now())
                                .phoneNumber("01000000001").encryptedPassword("test").build()).getId(),
                memberRepository.save(
                        Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now())
                                .phoneNumber("01000000002").encryptedPassword("test").build()).getId(),
                memberRepository.save(
                        Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now())
                                .phoneNumber("01000000003").encryptedPassword("test").build()).getId()

        );
    }
}