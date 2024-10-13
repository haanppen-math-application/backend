package com.hanpyeon.academyapi.board.service.comment;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.dto.CommentDeleteDto;
import com.hanpyeon.academyapi.board.dto.CommentRegisterDto;
import com.hanpyeon.academyapi.board.dto.CommentUpdateDto;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.BoardException;
import com.hanpyeon.academyapi.board.exception.NoSuchCommentException;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.board.dao.CommentRepository;
import com.hanpyeon.academyapi.board.service.comment.content.CommentContentManager;
import com.hanpyeon.academyapi.board.service.comment.register.CommentRegisterManager;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.security.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component
@WarnLoggable
@AllArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentRegisterManager commentRegisterManager;
    private final CommentContentManager commentContentManager;
    private final ImageService imageService;

    @Transactional
    public Long addComment(@Validated final CommentRegisterDto commentRegisterDto) {
        Comment comment = commentRegisterManager.register(commentRegisterDto);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void updateComment(@Validated final CommentUpdateDto commentUpdateDto) {
        final Comment comment = findComment(commentUpdateDto.commentId());
        validateOwnedMember(comment, commentUpdateDto.requestMemberId());

        if (commentUpdateDto.images() != null) {
            imageService.updateImage(comment, commentUpdateDto.images());
        }
        if (commentUpdateDto.content() != null) {
            commentContentManager.changeContentTo(comment, commentUpdateDto.content());
        }
    }

    @Transactional
    public void deleteComment(@Validated final CommentDeleteDto commentDeleteDto) {
        final Comment comment = findComment(commentDeleteDto.commentId());
        if (commentDeleteDto.role().equals(Role.TEACHER)) {
            if (!comment.getRegisteredMember().getId().equals(commentDeleteDto.requestMemberId())) {
                throw new BoardException("선생님은 본인 댓글만 삭제 가능", ErrorCode.COMMENT_EXCEPTION);
            }
        }
        comment.delete();
        commentRepository.delete(comment);
    }

    private Comment findComment(final Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchCommentException(ErrorCode.NO_SUCH_COMMENT));
    }

    private void validateOwnedMember(final Comment targetComment, final Long requestMemberId) {
        final Long questionRegisteredMemberId = targetComment.getRegisteredMember().getId();
        if (!questionRegisteredMemberId.equals(requestMemberId)) {
            throw new RequestDeniedException("본인이 작성한 댓글이 아닙니다", ErrorCode.DENIED_EXCEPTION);
        }
    }
}
