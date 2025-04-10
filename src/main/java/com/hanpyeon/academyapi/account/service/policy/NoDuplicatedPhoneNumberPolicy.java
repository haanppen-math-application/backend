package com.hanpyeon.academyapi.account.service.policy;

import com.hanpyeon.academyapi.account.entity.Member;
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
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
class NoDuplicatedPhoneNumberPolicy implements AccountPolicy {
    private final MemberRepository memberRepository;

    @Override
    public void verify(Member member) {
        if (memberRepository.existsByPhoneNumberAndRemovedIsFalse(member.getPhoneNumber())) {
            throw new AccountException("이미 등록된 전화번호", ErrorCode.ACCOUNT_POLICY);
        }
    }
}
