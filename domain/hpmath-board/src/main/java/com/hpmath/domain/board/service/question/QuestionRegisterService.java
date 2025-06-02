package com.hpmath.domain.board.service.question;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.QuestionCreatedEventPayload;
import com.hpmath.common.outbox.OutboxEventPublisher;
import com.hpmath.domain.board.dao.QuestionRepository;
import com.hpmath.domain.board.dto.QuestionRegisterCommand;
import com.hpmath.domain.board.entity.Question;
import com.hpmath.domain.board.entity.QuestionImage;
import com.hpmath.domain.board.exception.BoardException;
import com.hpmath.domain.board.service.question.validate.QuestionValidateManager;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
@Validated
public class QuestionRegisterService {
    private final QuestionRepository questionRepository;
    private final QuestionValidateManager questionValidateManager;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public Long addQuestion(@Valid final QuestionRegisterCommand questionRegisterDto) {
        final Question question = create(questionRegisterDto);
        questionValidateManager.validate(question);
        questionRepository.save(question);

        outboxEventPublisher.publishEvent(EventType.QUESTION_CREATED_EVENT, new QuestionCreatedEventPayload(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getSolved(),
                question.getOwnerMemberId(),
                question.getTargetMemberId(),
                question.getImages().stream()
                        .map(QuestionImage::getImageSrc)
                        .toList(),
                question.getRegisteredDateTime(),
                questionRepository.count()
        ));
        return question.getId();
    }

    private Question create(final QuestionRegisterCommand questionRegisterDto) {
        validateRequestMembersRole(questionRegisterDto.role());

        return Question.of(questionRegisterDto.images(), questionRegisterDto.title(), questionRegisterDto.content(),
                questionRegisterDto.requestMemberId(), questionRegisterDto.targetMemberId());
    }

    private void validateRequestMembersRole(final Role requestMemberRole) {
        if (requestMemberRole == Role.STUDENT) {
            return;
        }
        throw new BoardException(ErrorCode.BOARD_EXCEPTION);
    }
}
