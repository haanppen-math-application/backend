package com.hpmath.app.consumer.board.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.MemberDeletedEventPayload;
import com.hpmath.domain.board.service.question.QuestionDeleteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberDeletedBoardEventHandler implements EventHandler<MemberDeletedEventPayload> {
    private final QuestionDeleteService questionDeleteService;

    @Override
    @Transactional
    public void handle(Event<MemberDeletedEventPayload> event) {
        final MemberDeletedEventPayload payload = event.getPayload();

        questionDeleteService.deleteMemberInfos(List.of(payload.memberId()));
        log.debug("handled event {}", event);
    }

    @Override
    public boolean canHandle(Event<MemberDeletedEventPayload> event) {
        return event.getType().equals(EventType.MEMBER_DELETED_EVENT);
    }
}
