package com.hpmath.domain.member.service.policy;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.exceptions.AccountException;
import com.hpmath.common.ErrorCode;
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
