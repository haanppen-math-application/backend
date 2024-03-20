package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateDto;
import com.hanpyeon.academyapi.account.entity.Member;

public interface AccountUpdateManager {
    void update(final AccountUpdateDto accountUpdateDto, final Member member);
}
