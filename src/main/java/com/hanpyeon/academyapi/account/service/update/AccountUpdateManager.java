package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateDto;
import com.hanpyeon.academyapi.account.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountUpdateManager {

    private final List<AccountUpdateHandler> accountUpdateHandlers;

    public void update(final AccountUpdateDto accountUpdateDto, final Member member) {
        accountUpdateHandlers.stream()
                .forEach(accountUpdateHandler -> accountUpdateHandler.update(accountUpdateDto, member));
    }
}
