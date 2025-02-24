package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.entity.AccountMapper;
import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AccountLoader {
    private final MemberRepository memberRepository;
    private final AccountMapper accountMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Account loadAccount(final Long targetMemberId) {
        return accountMapper.mapToAccount(memberRepository.findMemberByIdAndRemovedIsFalse(targetMemberId)
                .orElseThrow(() -> new AccountException("찾을 수 없음", ErrorCode.NOT_REGISTERED_MEMBER)));
    }
}
