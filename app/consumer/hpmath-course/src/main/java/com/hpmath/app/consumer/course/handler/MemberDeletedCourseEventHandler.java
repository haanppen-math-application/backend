package com.hpmath.app.consumer.course.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.MemberDeletedEventPayload;
import com.hpmath.domain.course.application.port.out.DeleteStudentsPort;
import com.hpmath.domain.course.application.port.out.DeleteTeachersPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDeletedCourseEventHandler implements EventHandler<MemberDeletedEventPayload> {
    private final DeleteStudentsPort deleteStudentsPort;
    private final DeleteTeachersPort deleteTeachersPort;

    @Override
    public void handle(Event<MemberDeletedEventPayload> event) {
        final MemberDeletedEventPayload payload = event.getPayload();

        deleteStudentsPort.delete(payload.memberIds());
        deleteTeachersPort.deleteTeachers(payload.memberIds());

        log.debug("handled event: {}", event);
    }

    @Override
    public boolean applicable(Event<MemberDeletedEventPayload> event) {
        return event.getType().equals(EventType.MEMBER_DELETED_EVENT);
    }
}
