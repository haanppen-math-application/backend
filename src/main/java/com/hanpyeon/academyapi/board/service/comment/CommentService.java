package com.hanpyeon.academyapi.board.service.comment;

import com.hanpyeon.academyapi.board.dto.CommentAdoptDto;
import com.hanpyeon.academyapi.board.dto.CommentRegisterDto;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.exception.NoSuchCommentException;
import com.hanpyeon.academyapi.board.repository.CommentRepository;
import com.hanpyeon.academyapi.board.service.comment.adopt.CommentAdoptManager;
import com.hanpyeon.academyapi.board.service.comment.register.CommentRegisterManager;
import com.hanpyeon.academyapi.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentRegisterManager commentRegisterManager;
    private final CommentAdoptManager commentAdoptManager;

    @Transactional
    public Long addComment(@Validated final CommentRegisterDto commentRegisterDto) {
        Comment comment = commentRegisterManager.register(commentRegisterDto);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void adoptComment(@Validated final CommentAdoptDto commentAdoptDto) {
        final Comment comment = findComment(commentAdoptDto.commentId());
        commentAdoptManager.adopt(comment, commentAdoptDto.questionOwnerId());
    }

    private Comment findComment(final Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchCommentException(ErrorCode.NO_SUCH_COMMENT));
    }
}
