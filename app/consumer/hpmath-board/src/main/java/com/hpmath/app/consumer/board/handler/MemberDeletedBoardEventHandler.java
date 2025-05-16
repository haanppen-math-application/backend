package com.hpmath.app.consumer.board.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.MemberDeletedEventPayload;
import com.hpmath.domain.board.service.comment.CommentService;
import com.hpmath.domain.board.service.question.QuestionDeleteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberDeletedBoardEventHandler implements EventHandler<MemberDeletedEventPayload> {
    private final CommentService commentService;
    private final QuestionDeleteService questionDeleteService;

    @Override
    public void handle(Event<MemberDeletedEventPayload> event) {
        final List<Long> memberIds = event.getPayload().memberIds();
        questionDeleteService.deleteMemberInfos(memberIds);
        commentService.deleteMemberInfo(memberIds);

        log.debug("handled event {}", event);
    }

    @Override
    public boolean canHandle(Event<MemberDeletedEventPayload> event) {
        return event.getType().equals(EventType.MEMBER_DELETED_EVENT);
    }
}
