package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateCommand;
import com.hanpyeon.academyapi.account.service.Account;
import com.hanpyeon.academyapi.account.service.AccountAbstractFactory;
import com.hanpyeon.academyapi.account.service.AccountGrade;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
class AccountGradeUpdateHandler implements AccountUpdateCommandHandler {
    private final AccountAbstractFactory accountAbstractFactory;

    @Override
    public void update(Account targetAccount, AccountUpdateCommand updateCommand) {
        if (Objects.nonNull(updateCommand.grade())) {
            final AccountGrade accountGrade = accountAbstractFactory.getGrade(updateCommand.grade());
            targetAccount.updateGrade(accountGrade);
        }
    }
}
