package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.AccountRemoveDto;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        Assertions.assertEquals(memberRepository.findAll().size(), 4);
        accountRemoveService.removeAccount(new AccountRemoveDto(List.of(1L, 2L)));
        Assertions.assertEquals(memberRepository.findMembersByRoleAndRemovedIsFalse(Role.STUDENT).size(), 2);
    }

    void initTestData() {
        memberRepository.saveAll(List.of(Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now()).phoneNumber("01000000000").password("test").build()));
        memberRepository.saveAll(List.of(Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now()).phoneNumber("01000000001").password("test").build()));
        memberRepository.saveAll(List.of(Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now()).phoneNumber("01000000002").password("test").build()));
        memberRepository.saveAll(List.of(Member.builder().name("test").grade(10).role(Role.STUDENT).registeredDate(LocalDateTime.now()).phoneNumber("01000000003").password("test").build()));
    }
}