package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateDto;
import com.hanpyeon.academyapi.account.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class AccountGradeUpdateHandler implements AccountUpdateHandler {
    @Override
    public void update(AccountUpdateDto accountUpdateDto, Member member) {
        if (accountUpdateDto.grade() == null) {
            return;
        }
        member.setGrade(accountUpdateDto.grade());
    }
}
