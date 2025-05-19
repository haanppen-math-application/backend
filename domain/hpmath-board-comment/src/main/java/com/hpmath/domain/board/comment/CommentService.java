package com.hpmath.domain.board.comment;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.CommentDeletedEventPayload;
import com.hpmath.common.event.payload.CommentRegisteredEventPayLoad;
import com.hpmath.common.event.payload.CommentUpdatedEventPayload;
import com.hpmath.common.outbox.OutboxEventPublisher;
import com.hpmath.domain.board.comment.dto.CommentDetailResult;
import com.hpmath.domain.board.comment.dto.CommentQueryCommand;
import com.hpmath.domain.board.comment.dto.DeleteCommentCommand;
import com.hpmath.domain.board.comment.dto.RegisterCommentCommand;
import com.hpmath.domain.board.comment.dto.UpdateCommentCommand;
import com.hpmath.domain.board.comment.entity.Comment;
import com.hpmath.domain.board.comment.entity.CommentImage;
import com.hpmath.domain.board.comment.exception.CommentException;
import com.hpmath.domain.board.comment.repository.CommentRepository;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class CommentService {
    private final CommentRepository commentRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public Long addComment(@Valid final RegisterCommentCommand command) {
        final Comment comment = Comment.of(command.questionId(), command.requestMemberId(), command.content(), command.imageSrcs());
        validateCommentContent(comment);

        commentRepository.save(comment);
        final Long totalCommentCount = commentRepository.countByQuestionId(command.questionId());


        outboxEventPublisher.publishEvent(
                EventType.COMMENT_REGISTERED_EVENT,
                new CommentRegisteredEventPayLoad(
                        comment.getQuestionId(),
                        totalCommentCount,
                        comment.getId(),
                        comment.getContent(),
                        comment.getOwnerId()));

        return comment.getId();
    }

    @Transactional
    public void updateComment(@Valid final UpdateCommentCommand command) {
        final Comment comment = loadComment(command.commentId());
        List<String> deletedMedias = new ArrayList<>();

        if (command.content() != null) {
            comment.setContent(command.content());
        }

        if (!command.imageSources().isEmpty()) {
            deletedMedias = comment.changeImages(command.imageSources());
        }

        validateCommentContent(comment);

        outboxEventPublisher.publishEvent(
                EventType.COMMENT_UPDATED_EVENT,
                new CommentUpdatedEventPayload(
                        comment.getQuestionId(),
                        comment.getId(),
                        comment.getContent(),
                        comment.getOwnerId(),
                        deletedMedias));
    }

    @Transactional
    public void deleteComment(@Valid final DeleteCommentCommand command) {
        final Comment comment = loadComment(command.commentId());
        validateOwnedMember(comment, command.requestMemberId(), command.role());

        commentRepository.delete(comment);
        final Long totalCommentCount = commentRepository.countByQuestionId(comment.getQuestionId());

        outboxEventPublisher.publishEvent(
                EventType.COMMENT_DELETED_EVENT,
                new CommentDeletedEventPayload(
                        comment.getQuestionId(),
                        totalCommentCount,
                        comment.getId(),
                        comment.getOwnerId(),
                        comment.getImages().stream()
                                .map(CommentImage::getImageSrc)
                                .toList()
                ));
    }

    private void validateCommentContent(final Comment comment) {
        if (comment.getContent() == null && comment.getImages().isEmpty()) {
            throw new CommentException("이미지, 글 중 하나는 작성해야 합니다.", ErrorCode.ILLEGAL_COMMENT_EXCEPTION);
        }
    }

    private void validateOwnedMember(final Comment targetComment, final Long requestMemberId, final Role requestMemberRole) {
        if (requestMemberRole.equals(Role.TEACHER)) {
            if (!targetComment.getOwnerId().equals(requestMemberId)) {
                throw new CommentException(ErrorCode.COMMENT_EXCEPTION);
            }
        }
    }

    private Comment loadComment(final Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_EXCEPTION));
    }
}
