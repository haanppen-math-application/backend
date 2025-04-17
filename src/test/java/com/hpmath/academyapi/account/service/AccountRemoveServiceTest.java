package com.hpmath.academyapi.account.service;

import com.hpmath.academyapi.account.dto.AccountRemoveCommand;
import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.account.repository.MemberRepository;
import com.hpmath.academyapi.security.Role;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@Transactional
@Import(AccountRemoveService.class)
class AccountRemoveServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AccountRemoveService accountRemoveService;

    @Test
    void testDelete() {
        initTestData();
        accountRemoveService.removeAccount(new AccountRemoveCommand(List.of(1L, 2L)));
        Assertions.assertEquals(memberRepository.findMembersByRole(Role.STUDENT).size(), 2);
    }

    void initTestData() {
        memberRepository.save(Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now()).phoneNumber("01000000000").encryptedPassword("test").build());
        memberRepository.save(Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now()).phoneNumber("01000000001").encryptedPassword("test").build());
        memberRepository.save(Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now()).phoneNumber("01000000002").encryptedPassword("test").build());
        memberRepository.save(Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now()).phoneNumber("01000000003").encryptedPassword("test").build());
    }
}