package com.hanpyeon.academyapi.account.service.verify.policy;

import com.hanpyeon.academyapi.account.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountPolicyManager {
    private final List<AccountPolicy> policies;

    public void verify(final Member member) {
        policies.stream()
                .forEach(policy -> policy.verify(member));
    }
}
