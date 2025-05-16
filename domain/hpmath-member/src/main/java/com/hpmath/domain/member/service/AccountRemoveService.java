package com.hpmath.domain.member.service;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.AccountRemoveCommand;
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
