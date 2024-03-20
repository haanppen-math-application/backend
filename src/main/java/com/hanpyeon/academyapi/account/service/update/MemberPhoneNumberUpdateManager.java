package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateDto;
import com.hanpyeon.academyapi.account.entity.Member;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
class MemberPhoneNumberUpdateManager implements AccountUpdateManager {
    @Override
    public void update(AccountUpdateDto accountUpdateDto, Member member) {
        final String phoneNumber = accountUpdateDto.phoneNumber();
        if (Objects.nonNull(phoneNumber)) {
            member.setPhoneNumber(phoneNumber);
        }
    }
}
