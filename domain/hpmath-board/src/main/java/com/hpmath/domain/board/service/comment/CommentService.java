package com.hpmath.domain.board.service.comment;

import com.hpmath.domain.board.dao.CommentRepository;
import com.hpmath.domain.board.dto.CommentDeleteCommand;
import com.hpmath.domain.board.dto.CommentUpdateCommand;
import com.hpmath.domain.board.entity.Comment;
import com.hpmath.domain.board.exception.BoardException;
import com.hpmath.domain.board.exception.NoSuchCommentException;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void updateComment(final CommentUpdateCommand command) {
        final Comment comment = findComment(command.commentId());
        validateOwnedMember(comment, command.requestMemberId(), command.role());

        if (command.content() != null) {
            comment.setContent(command.content());
        }

        if (command.imageSources() != null && !command.imageSources().isEmpty()) {
            comment.changeImagesTo(command.imageSources());
        }
    }

    @Transactional
    public void deleteComment(final CommentDeleteCommand commentDeleteDto) {
        final Comment comment = findComment(commentDeleteDto.commentId());
        validateOwnedMember(comment, commentDeleteDto.requestMemberId(), commentDeleteDto.role());

        comment.delete();
        commentRepository.delete(comment);
    }

    private Comment findComment(final Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchCommentException(ErrorCode.NO_SUCH_COMMENT));
    }

    private void validateOwnedMember(final Comment targetComment, final Long requestMemberId, final Role requestMemberRole) {
        if (requestMemberRole.equals(Role.TEACHER)) {
            if (!targetComment.getRegisteredMember().equals(requestMemberId)) {
                throw new BoardException("선생님은 본인 댓글만 접근 가능", ErrorCode.COMMENT_EXCEPTION);
            }
        }
    }
}
