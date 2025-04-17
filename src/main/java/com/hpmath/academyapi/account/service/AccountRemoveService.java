package com.hpmath.academyapi.account.service;

import com.hpmath.academyapi.account.dto.AccountRemoveCommand;
import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.account.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountRemoveService {
    private final MemberRepository memberRepository;

    @Transactional
    public void removeAccount(final AccountRemoveCommand accountRemoveCommand) {
        List<Member> members = memberRepository.findMembersByIdIsInAndRemovedIsFalse(accountRemoveCommand.targetIds());
        members.stream().forEach(member -> member.remove());
    }
}
