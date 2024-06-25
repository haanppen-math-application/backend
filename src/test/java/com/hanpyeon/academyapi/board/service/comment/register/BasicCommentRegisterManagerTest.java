package com.hanpyeon.academyapi.board.service.comment.register;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.dto.CommentRegisterDto;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.exception.NotAllowedCommentException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.media.MediaMapper;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class BasicCommentRegisterManagerTest {
    @Mock
    ImageService imageService;
    @Mock
    QuestionRepository questionRepository;
    @Mock
    MemberRepository memberRepository;

    BoardMapper boardMapper = new BoardMapper(new MediaMapper());

    BasicCommentRegisterManager basicCommentRegisterManager;

    @BeforeEach
    void init() {
        basicCommentRegisterManager = new BasicCommentRegisterManager(imageService, questionRepository, memberRepository, boardMapper);
    }

    @Test
    void Comment_이미지만_작성_성공테스트() {
        final List<MultipartFile> images = List.of(new MockMultipartFile("test", "test".getBytes()));
        final List<Image> savedImages = List.of(new Image("test"));
        final Long questionId = 1L;
        final Long memberId = 2L;
        final String content = null;

        CommentRegisterDto commentRegisterDto = new CommentRegisterDto(questionId, memberId, content, images);
        Member member = Mockito.mock(Member.class);
        Question question = Mockito.mock(Question.class);

        Mockito.when(questionRepository.findById(commentRegisterDto.questionId()))
                .thenReturn(Optional.of(question));
        Mockito.when(memberRepository.findById(commentRegisterDto.memberId()))
                .thenReturn(Optional.of(member));
        Mockito.when(member.getRole())
                .thenReturn(Role.MANAGER);
        Mockito.when(imageService.saveImage(images))
                .thenReturn(savedImages);

        Assertions.assertDoesNotThrow(() -> basicCommentRegisterManager.register(commentRegisterDto));
    }

    @Test
    void Comment_글만_작성_성공_테스트() {
        final List<MultipartFile> images = Collections.emptyList();
        final List<Image> savedImages = Collections.emptyList();
        final Long questionId = 1L;
        final Long memberId = 2L;
        final String content = "test";

        CommentRegisterDto commentRegisterDto = new CommentRegisterDto(questionId, memberId, content, images);
        Member member = Mockito.mock(Member.class);
        Question question = Mockito.mock(Question.class);

        Mockito.when(questionRepository.findById(commentRegisterDto.questionId()))
                .thenReturn(Optional.of(question));
        Mockito.when(memberRepository.findById(commentRegisterDto.memberId()))
                .thenReturn(Optional.of(member));
        Mockito.when(member.getRole())
                .thenReturn(Role.MANAGER);
        Mockito.when(imageService.saveImage(images))
                .thenReturn(savedImages);

        Assertions.assertDoesNotThrow(() -> basicCommentRegisterManager.register(commentRegisterDto));
    }

    @Test
    void Comment_둘다_작성_안함_에러처리_테스트() {
        final List<MultipartFile> images = Collections.emptyList();
        final List<Image> savedImages = Collections.emptyList();
        final Long questionId = 1L;
        final Long memberId = 2L;
        final String content = null;

        CommentRegisterDto commentRegisterDto = new CommentRegisterDto(questionId, memberId, content, images);
        Member member = Mockito.mock(Member.class);
        Question question = Mockito.mock(Question.class);

        Mockito.when(questionRepository.findById(commentRegisterDto.questionId()))
                .thenReturn(Optional.of(question));
        Mockito.when(memberRepository.findById(commentRegisterDto.memberId()))
                .thenReturn(Optional.of(member));
        Mockito.when(member.getRole())
                .thenReturn(Role.MANAGER);
        Mockito.when(imageService.saveImage(images))
                .thenReturn(savedImages);

        Assertions.assertThrows(NotAllowedCommentException.class, () -> basicCommentRegisterManager.register(commentRegisterDto));
    }

    @Test
    void Comment_이미지_글_모두_작성_에러처리_테스트() {
        final List<MultipartFile> images = List.of(new MockMultipartFile("test", "test".getBytes()));
        final List<Image> savedImages = List.of(new Image("test"));
        final Long questionId = 1L;
        final Long memberId = 2L;
        final String content = "test";

        CommentRegisterDto commentRegisterDto = new CommentRegisterDto(questionId, memberId, content, images);
        Member member = Mockito.mock(Member.class);
        Question question = Mockito.mock(Question.class);

        Mockito.when(questionRepository.findById(commentRegisterDto.questionId()))
                .thenReturn(Optional.of(question));
        Mockito.when(memberRepository.findById(commentRegisterDto.memberId()))
                .thenReturn(Optional.of(member));
        Mockito.when(member.getRole())
                .thenReturn(Role.MANAGER);
        Mockito.when(imageService.saveImage(images))
                .thenReturn(savedImages);

        Assertions.assertThrows(NotAllowedCommentException.class, () -> basicCommentRegisterManager.register(commentRegisterDto));
    }

    @Test
    void 질문_못찾음_에러() {
        CommentRegisterDto commentRegisterDto = Mockito.mock(CommentRegisterDto.class);
        Member member = Mockito.mock(Member.class);

        Mockito.when(questionRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> basicCommentRegisterManager.register(commentRegisterDto))
                .isInstanceOf(NoSuchQuestionException.class);
    }

    @Test
    void 사용자_못찾음_에러() {
        Question question = Mockito.mock(Question.class);
        CommentRegisterDto commentRegisterDto = Mockito.mock(CommentRegisterDto.class);
        Mockito.when(questionRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(question));
        Mockito.when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> basicCommentRegisterManager.register(commentRegisterDto))
                .isInstanceOf(NoSuchMemberException.class);
    }
}