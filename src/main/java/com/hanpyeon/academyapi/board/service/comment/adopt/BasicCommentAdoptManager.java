package com.hanpyeon.academyapi.board.service.comment.adopt;

import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.exception.AlreadySolvedQuestionException;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class BasicCommentAdoptManager implements CommentAdoptManager {
    @Override
    public void adopt(final Comment targetComment, final Long requestMemberId) {
        isOwnedMember(targetComment, requestMemberId);
        isAlreadySolved(targetComment);

        applySelected(targetComment);
    }

    private void applySelected(final Comment targetComment) {
        targetComment.isSelected();
        targetComment.getQuestion().isSolved();
    }

    private void isOwnedMember(final Comment targetComment, final Long requestMemberId) {
        final Long questionRegisteredMemberId = targetComment.getRegisteredMember().getId();
        if (!questionRegisteredMemberId.equals(requestMemberId)) {
            throw new RequestDeniedException("본인이 작성한 질문이 아닙니다", ErrorCode.DENIED_EXCEPTION);
        }
    }

    private void isAlreadySolved(final Comment comment) {
        if (comment.getQuestion().getSolved()) {
            throw new AlreadySolvedQuestionException(ErrorCode.ALREADY_SOLVED_QUESTION_EXCEPTION);
        }
    }
}
