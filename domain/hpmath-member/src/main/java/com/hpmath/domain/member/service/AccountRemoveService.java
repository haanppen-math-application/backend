package com.hpmath.domain.member.service;

import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.MemberDeletedEventPayload;
import com.hpmath.common.outbox.OutboxEventPublisher;
import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.AccountRemoveCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "hpmath::member::info")
public class AccountRemoveService {
    private final MemberRepository memberRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    @CacheEvict(allEntries = true)
    public void removeAccount(final AccountRemoveCommand command) {
        final List<Member> members = memberRepository.findAllById(command.targetIds());
        members.forEach(member ->
                outboxEventPublisher.publishEvent(
                        EventType.MEMBER_DELETED_EVENT,
                        new MemberDeletedEventPayload(
                                member.getId(),
                                member.getPhoneNumber(),
                                member.getName(),
                                member.getRole(),
                                member.getGrade())
                ));
        memberRepository.deleteAllById(command.targetIds());

    }
}
