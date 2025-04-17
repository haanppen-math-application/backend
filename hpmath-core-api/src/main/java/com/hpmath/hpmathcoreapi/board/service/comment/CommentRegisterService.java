package com.hpmath.hpmathcoreapi.board.service.comment;

import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.board.dao.CommentRepository;
import com.hpmath.hpmathcoreapi.board.dao.QuestionRepository;
import com.hpmath.hpmathcoreapi.board.dto.CommentRegisterCommand;
import com.hpmath.hpmathcoreapi.board.entity.Comment;
import com.hpmath.hpmathcoreapi.board.entity.Question;
import com.hpmath.hpmathcoreapi.board.exception.NoSuchMemberException;
import com.hpmath.hpmathcoreapi.board.exception.NoSuchQuestionException;
import com.hpmath.hpmathcoreapi.board.exception.NotAllowedCommentException;
import com.hpmath.hpmathcoreapi.board.exception.RequestDeniedException;
import com.hpmath.hpmathcoreapi.board.mapper.BoardMapper;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import com.hpmath.hpmathcoreapi.media.entity.Image;
import com.hpmath.hpmathcoreapi.media.exception.MediaStoreException;
import com.hpmath.hpmathcoreapi.media.service.ImageService;
import com.hpmath.hpmathcoreapi.security.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentRegisterService {
    private final ImageService imageService;
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardMapper boardMapper;

    public Long register(final CommentRegisterCommand commentRegisterDto) {
        final Question question = findQuestion(commentRegisterDto.questionId());
        final Member member = findMember(commentRegisterDto.memberId());
        if (!imageService.isExists(commentRegisterDto.images())) {
            log.error(" cannt find images Source that included in comment : " + commentRegisterDto);
            throw new MediaStoreException("이미지를 찾을 수 없습니다.", ErrorCode.MEDIA_ACCESS_EXCEPTION);
        }
        final List<Image> images = imageService.loadImages(commentRegisterDto.images());
        final Comment comment = boardMapper.createComment(question, member, images, commentRegisterDto.content());
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

    protected void verifyMember(Member member) {
        if (member.getRole().equals(Role.STUDENT)) {
            throw new RequestDeniedException("학생은 질문에 대한 댓글을 달 수 없습니다", ErrorCode.DENIED_EXCEPTION);
        }
    }


    protected void verifyComment(Comment comment) {
        if (comment.getContent() == null && comment.getImages().isEmpty()) {
            throw new NotAllowedCommentException("댓글 = 이미지, 글 중 하나는 작성해야 합니다.", ErrorCode.ILLEGAL_COMMENT_EXCEPTION);
        }
    }
}
