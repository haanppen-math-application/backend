package com.hpmath.domain.member.service;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.RegisterMemberCommand;
import com.hpmath.domain.member.service.policy.AccountPolicyManager;
import com.hpmath.common.web.PasswordHandler;
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

    private final AccountPolicyManager accountPolicyManager;
    private final PasswordHandler passwordHandler;

    @Transactional
    public void register(@Valid final RegisterMemberCommand registerMemberCommand) {
        final String encryptedPassword = registerMemberCommand.password().getEncryptedPassword(passwordHandler);
        final Member member = buildMember(registerMemberCommand, encryptedPassword);

        accountPolicyManager.checkPolicy(member);
        memberRepository.save(member);
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
