package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateCommand;
import com.hanpyeon.academyapi.account.service.Account;
import com.hanpyeon.academyapi.account.service.AccountAbstractFactory;
import com.hanpyeon.academyapi.account.service.AccountName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
class MemberNameUpdateHandler implements AccountUpdateCommandHandler {
    private final AccountAbstractFactory accountAbstractFactory;

    @Override
    public void update(Account targetAccount, AccountUpdateCommand updateCommand) {
        if (Objects.nonNull(updateCommand.name())) {
            final AccountName accountName = accountAbstractFactory.getName(updateCommand.name());
            targetAccount.updateAccountName(accountName);
        }
    }
}
