package com.hpmath.domain.board.service.question;

import com.hpmath.client.member.MemberClient;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.QuestionDeletedEventPayload;
import com.hpmath.common.outbox.OutboxEventPublisher;
import com.hpmath.domain.board.dao.QuestionRepository;
import com.hpmath.domain.board.dto.QuestionDeleteCommand;
import com.hpmath.domain.board.entity.Question;
import com.hpmath.domain.board.exception.BoardException;
import com.hpmath.domain.board.exception.NoSuchQuestionException;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class QuestionDeleteService {
    private final MemberClient memberRoleClient;
    private final QuestionRepository questionRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public void deleteQuestion(@Valid final QuestionDeleteCommand questionDeleteDto) {
        final Question question = findQuestion(questionDeleteDto.questionId());
        hasPermission(questionDeleteDto.requestMemberId(), question.getOwnerMemberId());
        questionRepository.delete(question);

        outboxEventPublisher.publishEvent(EventType.QUESTION_DELETED_EVENT, new QuestionDeletedEventPayload(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getOwnerMemberId(),
                question.getTargetMemberId(),
                question.getRegisteredDateTime(),
                questionRepository.count()
        ));
    }

    @Transactional
    public void deleteMemberInfos(final List<Long> memberIds) {
        questionRepository.updateToNullOwnerMemberInfos(memberIds);
        questionRepository.updateToNullTargetMemberInfos(memberIds);
    }

    private void hasPermission(final Long requestMemberId, final Long questionOwnerId) {
        if (requestMemberId.equals(questionOwnerId)) {
            return;
        }
        if (memberRoleClient.isMatch(requestMemberId, Role.MANAGER, Role.TEACHER)) {
            return;
        }
        throw new BoardException("삭제 권한 부재", ErrorCode.DENIED_EXCEPTION);
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
