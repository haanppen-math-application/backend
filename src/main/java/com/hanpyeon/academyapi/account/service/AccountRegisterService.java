package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.RegisterMemberCommand;
import com.hanpyeon.academyapi.account.entity.AccountApplier;
import com.hanpyeon.academyapi.account.entity.AccountMapper;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.account.service.policy.AccountPolicyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRegisterService {
    private final AccountMapper accountMapper;
    private final AccountPolicyManager accountPolicyManager;
    private final AccountApplier accountApplier;

    public void register(final RegisterMemberCommand registerMemberDto) {
        final Account account = accountMapper.mapToAccount(registerMemberDto);
        accountPolicyManager.check(account);
        accountApplier.save(account);
    }
}
