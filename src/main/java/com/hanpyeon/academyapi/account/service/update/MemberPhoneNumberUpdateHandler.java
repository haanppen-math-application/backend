package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateCommand;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.service.Account;
import com.hanpyeon.academyapi.account.service.AccountAbstractFactory;
import com.hanpyeon.academyapi.account.service.AccountPhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
@RequiredArgsConstructor
class MemberPhoneNumberUpdateHandler implements AccountUpdateCommandHandler {
    private final AccountAbstractFactory accountAbstractFactory;

    @Override
    public void update(Account targetAccount, AccountUpdateCommand updateCommand) {
        if (Objects.nonNull(updateCommand.phoneNumber())) {
            final AccountPhoneNumber accountPhoneNumber = accountAbstractFactory.getPhoneNumber(updateCommand.phoneNumber());
            targetAccount.setPhoneNumber(accountPhoneNumber);
        }
    }
}
