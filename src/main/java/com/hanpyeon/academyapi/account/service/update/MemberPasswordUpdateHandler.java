package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateCommand;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.model.AccountAbstractFactory;
import com.hanpyeon.academyapi.account.service.password.AccountPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
class MemberPasswordUpdateHandler implements AccountUpdateCommandHandler {
    private final AccountAbstractFactory accountAbstractFactory;

    @Override
    public void update(Account targetAccount, AccountUpdateCommand updateCommand) {
        if (Objects.nonNull(updateCommand.prevPassword()) && Objects.nonNull(updateCommand.newPassword())) {
            final AccountPassword newPassword = accountAbstractFactory.getPassword(updateCommand.newPassword());
            targetAccount.changePassword(updateCommand.prevPassword(), newPassword);
        }
    }
}
