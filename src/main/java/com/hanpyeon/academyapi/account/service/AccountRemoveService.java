package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.AccountRemoveDto;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRemoveService {
    private final MemberRepository memberRepository;

    public void removeAccount(final AccountRemoveDto accountRemoveDto) {
        memberRepository.deleteAllById(accountRemoveDto.targetIds());
    }
}
