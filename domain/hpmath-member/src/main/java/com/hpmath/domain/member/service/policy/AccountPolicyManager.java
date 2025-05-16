package com.hpmath.domain.member.service.policy;

import com.hpmath.domain.member.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountPolicyManager {
    private final List<AccountPolicy> accountPolicies;

    /**
     * @throws com.hpmath.domain.member.exceptions.AccountException 정책 위반시 해당 객체를 던집니다.
     * @param member 정책 검사를 위한 파사드 객체 입니다.
     */
    public void checkPolicy(final Member member) {
        accountPolicies.forEach(policy -> policy.verify(member));
    }
}
