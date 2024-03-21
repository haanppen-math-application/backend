package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.AccountUpdateDto;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.AccessDeniedException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.account.service.update.AccountUpdateManager;
import com.hanpyeon.academyapi.account.service.verify.policy.AccountPolicyManager;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class AccountUpdateService {

    private final MemberRepository memberRepository;
    private final AccountUpdateManager accountUpdateManager;
    private final AccountPolicyManager accountPolicyManager;

    @Transactional
    public Long updateAccount(@Validated final AccountUpdateDto accountUpdateDto) {
        final Member member = memberRepository.findById(accountUpdateDto.requestMemberId())
                .orElseThrow(() -> new AccessDeniedException("자신이 소유한 계졍의 비밀번호만 바꿀 수 있습니다.", ErrorCode.NOT_OWNED_ACCOUNT));
        accountUpdateManager.update(accountUpdateDto, member);
        accountPolicyManager.verify(member);
        return member.getId();
    }
}
