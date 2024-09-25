package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.RegisterMemberCommand;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.security.PasswordHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final PasswordHandler passwordHandler;

    public Account mapToAccount(final RegisterMemberCommand registerMemberDto) {
        final AccountGrade accountGrade = AccountGrade.of(registerMemberDto.grade());
        final AccountRole accountRole = AccountRole.of(registerMemberDto.role());
        final AccountName accountName = AccountName.of(registerMemberDto.name());
        final AccountPhoneNumber accountPhoneNumber = AccountPhoneNumber.of(registerMemberDto.phoneNumber());
        final AccountPassword password = AccountPassword.of(registerMemberDto.password(), passwordHandler);

        return Account.of(accountPhoneNumber, accountName, accountRole, accountGrade, password);
    }

    public Member mapToEntity(final Account account) {
        return Member.builder()
                .role(account.getRole())
                .grade(account.getGrade())
                .name(account.getName())
                .phoneNumber(account.getPhoneNumber())
                .encryptedPassword(account.getEncryptedPassword())
                .build();
    }
}
