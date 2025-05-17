package com.hpmath.app.consumer.media.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.MemberDeletedEventPayload;
import com.hpmath.domain.media.service.MediaOwnerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDeleteMediaEventHandler implements EventHandler<MemberDeletedEventPayload> {
    private final MediaOwnerService mediaOwnerService;

    @Override
    public void handle(Event<MemberDeletedEventPayload> event) {
        final List<Long> deletedMemberIds = event.getPayload().memberIds();
        mediaOwnerService.updateMemberInfos(deletedMemberIds);
    }

    @Override
    public boolean supports(Event<MemberDeletedEventPayload> event) {
        return event.getType().equals(EventType.MEMBER_DELETED_EVENT);
    }
}
