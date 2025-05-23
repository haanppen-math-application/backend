package com.hpmath.app.consumer.directory.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.MemberDeletedEventPayload;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberDeleteDirectoryEventHandler implements EventHandler<MemberDeletedEventPayload> {
    private final DirectoryRepository directoryRepository;

    @Override
    @Transactional
    public void handle(Event<MemberDeletedEventPayload> event) {
        final MemberDeletedEventPayload payload = event.getPayload();

        directoryRepository.updateOwnerInfoToNullIdsIn(payload.memberId());
    }

    @Override
    public boolean supports(Event<MemberDeletedEventPayload> event) {
        return event.getType().equals(EventType.MEMBER_DELETED_EVENT);
    }
}
