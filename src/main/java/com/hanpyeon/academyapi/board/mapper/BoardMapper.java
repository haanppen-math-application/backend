package com.hanpyeon.academyapi.board.mapper;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.board.controller.Requests.CommentDeleteRequest;
import com.hanpyeon.academyapi.board.dto.CommentDetails;
import com.hanpyeon.academyapi.board.dto.CommentRegisterCommand;
import com.hanpyeon.academyapi.board.controller.Requests.CommentRegisterRequest;
import com.hanpyeon.academyapi.board.dto.CommentUpdateCommand;
import com.hanpyeon.academyapi.board.controller.Requests.CommentUpdateRequest;
import com.hanpyeon.academyapi.board.dto.MemberDetails;
import com.hanpyeon.academyapi.board.dto.QuestionDeleteCommand;
import com.hanpyeon.academyapi.board.controller.Requests.QuestionDeleteRequest;
import com.hanpyeon.academyapi.board.dto.QuestionDetails;
import com.hanpyeon.academyapi.board.dto.QuestionPreview;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterCommand;
import com.hanpyeon.academyapi.board.controller.Requests.QuestionRegisterRequest;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateCommand;
import com.hanpyeon.academyapi.board.controller.Requests.QuestionUpdateRequest;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.media.MediaMapper;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.security.Role;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardMapper {

    private final MediaMapper mediaMapper;

    public QuestionRegisterCommand createRegisterDto(final QuestionRegisterRequest questionRegisterRequestDto, final Long userId) {
        return QuestionRegisterCommand.builder()
                .title(questionRegisterRequestDto.title())
                .content(questionRegisterRequestDto.content())
                .requestMemberId(userId)
                .targetMemberId(questionRegisterRequestDto.targetMemberId())
                .images(questionRegisterRequestDto.images())
                .build();
    }

    public Question createQuestion(final Member requestMember, final Member targetMember, final List<Image> images, final String title, final String content) {
        return Question.builder()
                .title(title)
                .content(content)
                .ownerMember(requestMember)
                .targetMember(targetMember)
                .images(images)
                .build();
    }

    public QuestionDetails createQuestionDetails(final Question question) {
        return QuestionDetails.builder()
                .questionId(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .solved(question.getSolved())
                .viewCount(question.getViewCount())
                .registeredMember(createMemberDetails(question.getOwnerMember()))
                .targetMember(createMemberDetails(question.getTargetMember()))
                .registeredDateTime(question.getRegisteredDateTime())
                .imageUrls(question.getImages().stream()
                        .map(mediaMapper::createImageUrlDto)
                        .toList())
                .comments(question.getComments().stream()
                        .map(this::createCommentDetails)
                        .toList())
                .build();
    }

    public MemberDetails createMemberDetails(final Member member) {
        if (Objects.isNull(member)) {
            return null;
        }
        return MemberDetails.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .memberGrade(member.getGrade())
                .role(member.getRole().getIdentifier())
                .build();
    }

    public CommentDetails createCommentDetails(final Comment comment) {
        return CommentDetails.builder()
                .commentId(comment.getId())
                .selected(comment.getAdopted())
                .images(comment.getImages().stream()
                        .map(mediaMapper::createImageUrlDto)
                        .toList())
                .registeredDateTime(comment.getRegisteredDateTime())
                .content(comment.getContent())
                .registeredMemberDetails(createMemberDetails(comment.getRegisteredMember()))
                .build();
    }

    public QuestionPreview createQuestionPreview(final Question question) {
        return QuestionPreview.builder()
                .title(question.getTitle())
                .registeredDateTime(question.getRegisteredDateTime())
                .questionId(question.getId())
                .solved(question.getSolved())
                .commentCount(question.getComments().size())
                .viewCount(question.getViewCount())
                .owner(createMemberDetails(question.getOwnerMember()))
                .target(createMemberDetails(question.getTargetMember()))
                .build();
    }

    public Comment createComment(final Question question, final Member member, final List<Image> images, final String content) {
        return Comment.builder()
                .question(question)
                .images(images)
                .registeredMember(member)
                .content(content)
                .build();
    }
    public CommentUpdateCommand createCommentUpdateDto(final CommentUpdateRequest commentUpdateRequestDto, final Long requestMemberId, final Role role) {
        return CommentUpdateCommand.builder()
                .commentId(commentUpdateRequestDto.commentId())
                .content(commentUpdateRequestDto.content())
                .role(role)
                .requestMemberId(requestMemberId)
                .imageSources(commentUpdateRequestDto.imageSources())
                .build();
    }

    public CommentRegisterCommand createCommentRegisterDto(final CommentRegisterRequest registerRequestDto, final Long memberId) {
        return CommentRegisterCommand.builder()
                .questionId(registerRequestDto.questionId())
                .content(registerRequestDto.content())
                .memberId(memberId)
                .images(registerRequestDto.images())
                .build();
    }

    public CommentDeleteRequest createCommentDeleteDto(final Long commentId, final Long requestMemberId, final Role role) {
        return CommentDeleteRequest.builder()
                .requestMemberId(requestMemberId)
                .commentId(commentId)
                .role(role)
                .build();
    }
    public QuestionUpdateCommand createQuestionUpdateDto(final QuestionUpdateRequest questionUpdateRequestDto, final Long requestMemberId, final Role memberRole) {
        return QuestionUpdateCommand.builder()
                .memberRole(memberRole)
                .title(questionUpdateRequestDto.title())
                .content(questionUpdateRequestDto.content())
                .requestMemberId(requestMemberId)
                .questionId(questionUpdateRequestDto.questionId())
                .targetMemberId(questionUpdateRequestDto.targetMemberId())
                .imageSources(questionUpdateRequestDto.imageSources())
                .build();
    }

    public QuestionDeleteCommand createQuestionDeleteDto(final QuestionDeleteRequest questionDeleteRequestDto, final Long requestMemberId, final Role role) {
        return new QuestionDeleteCommand(questionDeleteRequestDto.questionId(), role, requestMemberId);
    }
}
