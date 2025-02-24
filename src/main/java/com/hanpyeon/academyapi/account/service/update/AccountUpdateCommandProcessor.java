package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateCommand;
import com.hanpyeon.academyapi.account.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountUpdateCommandProcessor {
    private final List<AccountUpdateCommandHandler> commandHandlers;

    @Transactional(propagation = Propagation.MANDATORY)
    public Account processCommand(final Account targetAccount, final AccountUpdateCommand updateCommand) {
        commandHandlers.stream().forEach(accountUpdateHandler -> accountUpdateHandler.update(targetAccount, updateCommand));
        return targetAccount;
    }
}
