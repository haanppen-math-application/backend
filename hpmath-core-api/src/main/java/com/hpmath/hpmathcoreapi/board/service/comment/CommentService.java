package com.hpmath.hpmathcoreapi.board.service.comment;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.aspect.log.WarnLoggable;
import com.hpmath.hpmathcoreapi.board.dao.CommentRepository;
import com.hpmath.hpmathcoreapi.board.dto.CommentDeleteCommand;
import com.hpmath.hpmathcoreapi.board.dto.CommentUpdateCommand;
import com.hpmath.hpmathcoreapi.board.entity.Comment;
import com.hpmath.hpmathcoreapi.board.exception.BoardException;
import com.hpmath.hpmathcoreapi.board.exception.NoSuchCommentException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcoreapi.media.entity.Image;
import com.hpmath.hpmathcoreapi.media.service.ImageService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component
@WarnLoggable
@AllArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final ImageService imageService;

    @Transactional
    public void updateComment(@Validated final CommentUpdateCommand commentUpdateDto) {
        final Comment comment = findComment(commentUpdateDto.commentId());
        validateOwnedMember(comment, commentUpdateDto.requestMemberId(), commentUpdateDto.role());

        if (commentUpdateDto.content() != null) {
            comment.setContent(commentUpdateDto.content());
        }
        final List<Image> images = imageService.loadImages(commentUpdateDto.imageSources());
        comment.changeImagesTo(images);
    }

    @Transactional
    public void deleteComment(@Validated final CommentDeleteCommand commentDeleteDto) {
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
            if (!targetComment.getRegisteredMember().getId().equals(requestMemberId)) {
                throw new BoardException("선생님은 본인 댓글만 접근 가능", ErrorCode.COMMENT_EXCEPTION);
            }
        }
    }
}
