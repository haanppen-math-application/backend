package com.hpmath.hpmathcoreapi.account.service.policy;

import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.account.exceptions.AccountException;
import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
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
