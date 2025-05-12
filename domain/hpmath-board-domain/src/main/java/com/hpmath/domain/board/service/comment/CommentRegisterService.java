package com.hpmath.domain.board.service.comment;

import com.hpmath.domain.board.dao.CommentRepository;
import com.hpmath.domain.board.dao.QuestionRepository;
import com.hpmath.domain.board.dto.CommentRegisterCommand;
import com.hpmath.domain.board.entity.Comment;
import com.hpmath.domain.board.entity.Question;
import com.hpmath.domain.board.exception.NoSuchMemberException;
import com.hpmath.domain.board.exception.NoSuchQuestionException;
import com.hpmath.domain.board.exception.NotAllowedCommentException;
import com.hpmath.domain.board.exception.RequestDeniedException;
import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
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
    private final MemberRepository memberRepository;

    public Long register(final CommentRegisterCommand commentRegisterDto) {
        final Question question = findQuestion(commentRegisterDto.questionId());
        final Member member = findMember(commentRegisterDto.memberId());
        final Comment comment = Comment.createComment(
                commentRegisterDto.content(),
                member,
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

    private Member findMember(final Long memberId) {
        Member member = memberRepository.findMemberByIdAndRemovedIsFalse(memberId)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER));
        verifyMember(member);
        return member;
    }

    private void verifyMember(Member member) {
        if (member.getRole().equals(Role.STUDENT)) {
            throw new RequestDeniedException("학생은 질문에 대한 댓글을 달 수 없습니다", ErrorCode.DENIED_EXCEPTION);
        }
    }

    private void verifyComment(Comment comment) {
        if (comment.getContent() == null && comment.getImages().isEmpty()) {
            throw new NotAllowedCommentException("댓글 = 이미지, 글 중 하나는 작성해야 합니다.", ErrorCode.ILLEGAL_COMMENT_EXCEPTION);
        }
    }
}
