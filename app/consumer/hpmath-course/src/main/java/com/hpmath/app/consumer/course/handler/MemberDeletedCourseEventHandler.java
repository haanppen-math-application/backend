package com.hpmath.app.consumer.course.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.MemberDeletedEventPayload;
import com.hpmath.domain.course.repository.CourseRepository;
import com.hpmath.domain.course.repository.CourseStudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDeletedCourseEventHandler implements EventHandler<MemberDeletedEventPayload> {
    private final CourseStudentRepository courseStudentRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public void handle(Event<MemberDeletedEventPayload> event) {
        final MemberDeletedEventPayload payload = event.getPayload();

        courseRepository.updateTeacherToNullIn(List.of(payload.memberId()));
        courseStudentRepository.deleteCourseStudentsByStudentIdIn(List.of(payload.memberId()));

        log.debug("handled event: {}", event);
    }

    @Override
    public boolean applicable(Event<MemberDeletedEventPayload> event) {
        return event.getType().equals(EventType.MEMBER_DELETED_EVENT);
    }
}
