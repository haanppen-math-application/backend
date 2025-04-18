package com.hpmath.hpmathcoreapi.account.service;

import com.hpmath.hpmathcoreapi.account.dto.RegisterMemberCommand;
import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.account.service.policy.AccountPolicyManager;
import com.hpmath.hpmathwebcommon.PasswordHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountRegisterService {
    private final MemberRepository memberRepository;

    private final AccountPolicyManager accountPolicyManager;
    private final PasswordHandler passwordHandler;

    @Transactional
    public void register(final RegisterMemberCommand registerMemberCommand) {
        final String encryptedPassword = getEncryptedPassword(registerMemberCommand.password().getEncryptedPassword(passwordHandler));
        final Member member = buildMember(registerMemberCommand, encryptedPassword);

        accountPolicyManager.checkPolicy(member);
        memberRepository.save(member);
    }

    private String getEncryptedPassword(final String password) {
        return passwordHandler.getEncodedPassword(password);
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
