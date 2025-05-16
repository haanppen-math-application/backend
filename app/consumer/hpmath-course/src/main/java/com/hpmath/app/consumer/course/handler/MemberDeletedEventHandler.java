package com.hpmath.app.consumer.course.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.MemberDeletedEventPayload;
import com.hpmath.domain.course.application.port.out.DeleteStudentsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDeletedEventHandler implements EventHandler<MemberDeletedEventPayload> {
    private final DeleteStudentsPort deleteStudentsPort;

    @Override
    @Transactional
    public void handle(Event<MemberDeletedEventPayload> event) {
        log.debug("Handling MemberDeletedEventPayload: {}", event);
        final MemberDeletedEventPayload payload = event.getPayload();
        deleteStudentsPort.delete(payload.memberIds());
    }

    @Override
    public boolean applicable(Event<MemberDeletedEventPayload> event) {
        return event.getType().equals(EventType.MEMBER_DELETED_EVENT);
    }
}
