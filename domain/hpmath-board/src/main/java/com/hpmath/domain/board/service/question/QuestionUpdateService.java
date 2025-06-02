package com.hpmath.domain.board.service.question;

import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.QuestionUpdatedEventPayload;
import com.hpmath.common.outbox.OutboxEventPublisher;
import com.hpmath.domain.board.dao.QuestionRepository;
import com.hpmath.domain.board.dto.QuestionUpdateCommand;
import com.hpmath.domain.board.entity.Question;
import com.hpmath.domain.board.entity.QuestionImage;
import com.hpmath.domain.board.exception.NoSuchQuestionException;
import com.hpmath.domain.board.exception.RequestDeniedException;
import com.hpmath.domain.board.service.question.validate.QuestionValidateManager;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component
@AllArgsConstructor
@Validated
public class QuestionUpdateService {
    private final QuestionRepository questionRepository;
    private final QuestionValidateManager questionValidateManager;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public Long updateQuestion(@Valid final QuestionUpdateCommand questionUpdateDto) {
        final Question target = findQuestion(questionUpdateDto.questionId());
        verifyAccess(target.getOwnerMemberId(), questionUpdateDto.requestMemberId(), questionUpdateDto.memberRole());

        target.setTitle(questionUpdateDto.title());
        target.setContent(questionUpdateDto.content());
        target.changeImages(questionUpdateDto.imageSources());
        target.setTargetMemberId(questionUpdateDto.targetMemberId());
        questionValidateManager.validate(target);

        outboxEventPublisher.publishEvent(EventType.QUESTION_UPDATED_EVENT, new QuestionUpdatedEventPayload(
                target.getId(),
                target.getTitle(),
                target.getContent(),
                target.getSolved(),
                target.getOwnerMemberId(),
                target.getTargetMemberId(),
                target.getImages().stream()
                        .map(QuestionImage::getImageSrc)
                        .toList(),
                target.getRegisteredDateTime()
        ));
        return target.getId();
    }

    private void verifyAccess(final Long ownedMemberId, final Long requestMemberId, final Role role) {
        if (role.equals(Role.ADMIN) || role.equals(Role.TEACHER) || role.equals(Role.MANAGER)) {
            return;
        }
        if (!requestMemberId.equals(ownedMemberId)) {
            throw new RequestDeniedException("본인 질문이 아닙니다", ErrorCode.DENIED_EXCEPTION);
        }
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
