package com.hpmath.domain.member.service;

import com.hpmath.common.Role;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.AccountUpdateCommand;
import com.hpmath.domain.member.dto.Password;
import com.hpmath.domain.member.dto.RegisterMemberCommand;
import com.hpmath.domain.member.exceptions.AccountException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = """
spring.jpa.show-sql=true
""")
class AccountUpdateServiceTest {
    @Autowired
    private AccountRegisterService accountRegisterService;
    @Autowired
    private AccountUpdateService accountUpdateService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    void 기존_전화번호_일때_정상_수정() {
        final String phoneNumber1 = "01000000000";
        final String phoneNumber2 = "01000000002";
        accountRegisterService.register(new RegisterMemberCommand("test", 1, phoneNumber1, Role.TEACHER, new Password("123456")));
        accountRegisterService.register(new RegisterMemberCommand("test", 1, phoneNumber2, Role.TEACHER, new Password("123456")));

        final Long memberId = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(phoneNumber1).orElseThrow().getId();

        entityManager.flush();
        entityManager.clear();

        Assertions.assertDoesNotThrow(() -> accountUpdateService.updateMember(new AccountUpdateCommand(memberId, phoneNumber1, "test1", null, null)));
    }

    @Test
    @Transactional
    void 중복안되는_전화번호로_수정_했을때_정상_수정() {
        final String phoneNumber1 = "01000000000";
        final String phoneNumber2 = "01000000002";
        final String newPhoneNumber = "01000000003";

        accountRegisterService.register(new RegisterMemberCommand("test", 1, phoneNumber1, Role.TEACHER, new Password("123456")));
        accountRegisterService.register(new RegisterMemberCommand("test", 1, phoneNumber2, Role.TEACHER, new Password("123456")));

        final Long memberId = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(phoneNumber1).orElseThrow().getId();

        entityManager.flush();
        entityManager.clear();

        Assertions.assertDoesNotThrow(() -> accountUpdateService.updateMember(new AccountUpdateCommand(memberId, newPhoneNumber, "test1", null, null)));
    }

    @Test
    @Transactional
    void 중복되는_전화번호_에러() {
        final String phoneNumber1 = "01000000000";
        final String phoneNumber2 = "01000000002";

        accountRegisterService.register(new RegisterMemberCommand("test", 1, phoneNumber1, Role.TEACHER, new Password("123456")));
        accountRegisterService.register(new RegisterMemberCommand("test", 1, phoneNumber2, Role.TEACHER, new Password("123456")));

        final Long memberId = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(phoneNumber1).orElseThrow().getId();

        entityManager.flush();
        entityManager.clear();

        Assertions.assertThrows(AccountException.class, () -> accountUpdateService.updateMember(new AccountUpdateCommand(memberId, phoneNumber2, "test1", null, null)));
    }
}