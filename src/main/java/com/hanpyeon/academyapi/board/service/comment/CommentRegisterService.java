package com.hanpyeon.academyapi.board.service.comment;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.dto.CommentRegisterCommand;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.exception.NotAllowedCommentException;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.exception.MediaStoreException;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.security.Role;
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
    private final MemberRepository memberRepository;
    private final BoardMapper boardMapper;
    /**
     *
     * @param commentRegisterDto
     * @return Comment
     */
    public Comment register(final CommentRegisterCommand commentRegisterDto) {
        Question question = findQuestion(commentRegisterDto.questionId());
        Member member = findMember(commentRegisterDto.memberId());
        if (!imageService.isExists(commentRegisterDto.images())) {
            log.error(" cannt find images Source that included in comment : " + commentRegisterDto);
            throw new MediaStoreException("이미지를 찾을 수 없습니다.", ErrorCode.MEDIA_ACCESS_EXCEPTION);
        }
        final List<Image> images = imageService.loadImages(commentRegisterDto.images());
        final Comment comment = boardMapper.createComment(question, member, images, commentRegisterDto.content());
        question.addComment(comment);
        question.solved();
        verifyComment(comment);
        return comment;
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
