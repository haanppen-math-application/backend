package com.hpmath.domain.member.service;

import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.MemberDeletedEvent;
import com.hpmath.common.outbox.OutboxEventPublisher;
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
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public void removeAccount(final AccountRemoveCommand command) {
        memberRepository.deleteAllById(command.targetIds());

        outboxEventPublisher.publishEvent(
                EventType.MEMBER_DELETED_EVENT,
                new MemberDeletedEvent(command.targetIds()));
    }
}
