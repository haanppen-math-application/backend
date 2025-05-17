package com.hpmath.app.consumer.directory.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.MemberDeletedEventPayload;
import com.hpmath.domain.directory.service.delete.DirectoryDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDeleteDirectoryEventHandler implements EventHandler<MemberDeletedEventPayload> {
    private final DirectoryDeleteService directoryDeleteService;

    @Override
    public void handle(Event<MemberDeletedEventPayload> event) {
        final MemberDeletedEventPayload payload = event.getPayload();
        directoryDeleteService.deleteOwnerInformation(payload.memberIds());
    }

    @Override
    public boolean supports(Event<MemberDeletedEventPayload> event) {
        return event.getType().equals(EventType.MEMBER_DELETED_EVENT);
    }
}
