package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateDto;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.PasswordHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MemberPasswordUpdateHandler implements AccountUpdateHandler {
    private final PasswordHandler passwordHandler;

    @Override
    public void update(AccountUpdateDto accountUpdateDto, Member member) {
        if (Objects.nonNull(accountUpdateDto.newPassword())) {
            verifyPreviousPassword(member, accountUpdateDto.prevPassword());

            final String encodedPassword = passwordHandler.getEncodedPassword(accountUpdateDto.newPassword());
            member.setPassword(encodedPassword);
        }
    }

    private void verifyPreviousPassword(final Member member, final String prevPassword) {
        if (prevPassword == null || !passwordHandler.matches(prevPassword, member.getPassword())) {
            throw new AccountException("prevPassword가 올바르지 않습니다.", ErrorCode.INVALID_PASSWORD_EXCEPTION);
        }
    }
}
