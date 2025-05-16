package com.hpmath.domain.directory.service.comment;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.directory.dao.CommentRepository;
import com.hpmath.domain.directory.dao.QuestionRepository;
import com.hpmath.domain.directory.dto.CommentRegisterCommand;
import com.hpmath.domain.directory.entity.Comment;
import com.hpmath.domain.directory.entity.Question;
import com.hpmath.domain.directory.exception.NoSuchQuestionException;
import com.hpmath.domain.directory.exception.NotAllowedCommentException;
import com.hpmath.domain.directory.exception.RequestDeniedException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentRegisterService {
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;
    private final MemberClient memberRoleClient;

    public Long register(final CommentRegisterCommand commentRegisterDto) {
        checkRequestMemberIsStudent(commentRegisterDto.memberId());

        final Question question = findQuestion(commentRegisterDto.questionId());
        final Comment comment = Comment.createComment(
                commentRegisterDto.content(),
                commentRegisterDto.memberId(),
                question,
                commentRegisterDto.images()
        );
        question.addComment(comment);
        question.solved();
        verifyComment(comment);
        commentRepository.save(comment);
        return comment.getId();
    }

    private Question findQuestion(final Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
        return question;
    }

    private void checkRequestMemberIsStudent(final Long memberId) {
        if (memberRoleClient.isMatch(memberId, Role.STUDENT)) {
            throw new RequestDeniedException("학생은 질문에 대한 댓글을 달 수 없습니다", ErrorCode.DENIED_EXCEPTION);
        }
    }

    private void verifyComment(Comment comment) {
        if (comment.getContent() == null && comment.getImages().isEmpty()) {
            throw new NotAllowedCommentException("댓글 = 이미지, 글 중 하나는 작성해야 합니다.", ErrorCode.ILLEGAL_COMMENT_EXCEPTION);
        }
    }
}
