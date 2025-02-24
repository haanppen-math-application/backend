package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.AccountUpdateCommand;
import com.hanpyeon.academyapi.account.dto.MyAccountInfo;
import com.hanpyeon.academyapi.account.entity.AccountApplier;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.service.update.AccountUpdateCommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class AccountUpdateService {
    private final AccountLoader accountLoader;
    private final AccountUpdateCommandProcessor accountUpdateProcessor;
    private final AccountApplier accountApplier;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateAccount(@Validated final AccountUpdateCommand updateCommand) {
        final Account targetAccount = accountLoader.loadAccount(updateCommand.targetMemberId());
        accountUpdateProcessor.processCommand(targetAccount, updateCommand);
        accountApplier.applyAccount(targetAccount);
    }

    @Transactional(readOnly = true)
    public MyAccountInfo getMyInfo(final Long requestMemberId) {
        final Account account = accountLoader.loadAccount(requestMemberId);
        return new MyAccountInfo(account.getAccountName().getName(), account.getPhoneNumber().getPhoneNumber(), account.getAccountRole().getRole(), account.getGrade().getGrade());
    }
}
