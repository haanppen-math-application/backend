package com.hpmath.domain.member.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.web.PasswordHandler;
import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.RegisterMemberCommand;
import com.hpmath.domain.member.exceptions.AccountException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class AccountRegisterService {
    private final MemberRepository memberRepository;
    private final PasswordHandler passwordHandler;

    @Transactional
    public void register(@Valid final RegisterMemberCommand command) {
        final String encryptedPassword = command.password().getEncryptedPassword(passwordHandler);
        checkPhoneNumberDuplicated(command.phoneNumber());
        final Member member = buildMember(command, encryptedPassword);

        memberRepository.save(member);
    }

    public void checkPhoneNumberDuplicated(final String phoneNumber) {
        if (memberRepository.existsByPhoneNumberAndRemovedIsFalse(phoneNumber)) {
            throw new AccountException("이미 등록된 전화번호", ErrorCode.ACCOUNT_POLICY);
        }
    }

    private Member buildMember(final RegisterMemberCommand registerMemberCommand, final String encryptedPassword) {
        return Member.builder()
                .encryptedPassword(encryptedPassword)
                .role(registerMemberCommand.role())
                .grade(registerMemberCommand.grade())
                .name(registerMemberCommand.name())
                .phoneNumber(registerMemberCommand.phoneNumber())
                .build();
    }
}
