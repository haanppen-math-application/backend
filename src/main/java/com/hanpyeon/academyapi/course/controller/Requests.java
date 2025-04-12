package com.hanpyeon.academyapi.course.controller;

import com.hanpyeon.academyapi.course.application.dto.AttachmentChunkResult;
import com.hanpyeon.academyapi.course.application.dto.CourseRegisterCommand;
import com.hanpyeon.academyapi.course.application.dto.CourseUpdateCommand;
import com.hanpyeon.academyapi.course.application.dto.DeleteCourseCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoMediaUpdateSequenceCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoRegisterCommand;
import com.hanpyeon.academyapi.course.application.dto.ModifyMemoTextCommand;
import com.hanpyeon.academyapi.course.application.dto.RegisterAttachmentChunkCommand;
import com.hanpyeon.academyapi.course.application.dto.RegisterMemoMediaCommand;
import com.hanpyeon.academyapi.course.application.dto.RegisterStudentCommand;
import com.hanpyeon.academyapi.course.application.dto.UpdateCourseStudentsCommand;
import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

public class Requests {
    record QueryMemoByCourseIdAndDateRequest(
            @Nonnull
            Long courseId,
            @Nonnull
            LocalDate localDate
    ) {
        MemoQueryByCourseIdAndDateCommand toCommand() {
            return new MemoQueryByCourseIdAndDateCommand(courseId, localDate);
        }
    }

    record DeleteCourseRequest(
            Long courseId
    ) {
        DeleteCourseCommand toCommand(final MemberPrincipal memberPrincipal) {
            return new DeleteCourseCommand(courseId, memberPrincipal.role(), memberPrincipal.memberId());
        }
    }

    record UpdateCourseStudentsRequest(
            List<Long> studentIds
    ) {
        UpdateCourseStudentsCommand toCommand(final Long requestMemberId, final Long courseId) {
            return new UpdateCourseStudentsCommand(requestMemberId, courseId, studentIds());
        }
    }

    record CourseUpdateRequest(
            String courseName,
            Long newTeacherId
    ) {
        CourseUpdateCommand toCommand(final Long courseId, final Long requestMemberId) {
            return new CourseUpdateCommand(
                    courseId,
                    requestMemberId,
                    courseName,
                    newTeacherId
            );
        }
    }

    record CourseRegisterRequest(
            @NotNull
            String courseName,
            @NotNull
            Long teacherId,
            List<Long> students
    ) {
        CourseRegisterCommand toCommand(final Long requestMemberId, final Role role) {
            return new CourseRegisterCommand(
                    courseName(),
                    teacherId(),
                    students(),
                    requestMemberId,
                    role
            );
        }
    }

    record RegisterStudentRequest(
            @NotNull
            Long courseId,
            @NotNull
            List<Long> targetStudentIds
    ) {
        RegisterStudentCommand toCommand(
                final Long requestMemberId
        ) {
            return new RegisterStudentCommand(
                    requestMemberId,
                    courseId(),
                    targetStudentIds()
            );
        }
    }

    record MemoTextModifyRequest(
            Long memoId,
            String title,
            String content
    ) {
        ModifyMemoTextCommand toCommand(final Long requestMemberId) {
            return new ModifyMemoTextCommand(memoId, title, content, requestMemberId);
        }
    }

    record RegisterMemoRequest(
            @Nonnull
            Long targetCourseId,
            @Nonnull
            String title,
            @Nonnull
            String content,
            @Nonnull
            LocalDate registerTargetDateTime
    ) {
        MemoRegisterCommand toCommand(final Long requestMemberId) {
            return new MemoRegisterCommand(
                    requestMemberId,
                    this.targetCourseId,
                    this.title,
                    this.content,
                    this.registerTargetDateTime
            );
        }
    }

    record RegisterAttachmentWithChunkRequest(
            @Nonnull
            Long memoMediaId,
            @NotBlank
            String fileName,
            @Nonnull
            MultipartFile chunkedFile,
            @Nonnull
            Long totalChunkCount,
            @Nonnull
            Long currChunkIndex,
            @Nonnull
            Boolean isLast,
            @Nonnull
            String extension
    ) {
        RegisterAttachmentChunkCommand toCommand(final Long requestMemberId) {
            return new RegisterAttachmentChunkCommand(
                    requestMemberId,
                    memoMediaId(),
                    chunkedFile(),
                    fileName(),
                    totalChunkCount(),
                    currChunkIndex(),
                    isLast(),
                    extension()
            );
        }
    }

    record RegisterAttachmentChunkResponse(
            Long nextChunkIndex,
            Long remainSize,
            Boolean needMore,
            Boolean isWrongChunk,
            String errorMessage
    ) {
        static RegisterAttachmentChunkResponse of(final AttachmentChunkResult result) {
            return new RegisterAttachmentChunkResponse(
                    result.getNextRequireChunkIndex(),
                    result.getRemainSize(),
                    result.getNeedMore(),
                    result.getIsWrongChunk(),
                    result.getErrorMessage()
            );
        }
    }

    record RegisterMemoMediaRequest(
            @Nonnull
            String mediaSource,
            @Nonnull
            Long memoId
    ) {
        RegisterMemoMediaCommand toCommand(final Long requestMemberId) {
            return new RegisterMemoMediaCommand(mediaSource, memoId, requestMemberId);
        }
    }

    record UpdateMemoMediaRequest(
            @Nonnull
            Long memoId,
            @Nonnull
            List<MediaSequenceUpdateRequest> sequenceUpdateRequests
    ) {
        UpdateMediaMemoCommand toCommand(final Long requestMemberId) {
            final List<MemoMediaUpdateSequenceCommand> updateMediaMemoCommands = sequenceUpdateRequests.stream()
                    .map(mediaInfo -> mediaInfo.toCommand())
                    .collect(Collectors.toList());
            return new UpdateMediaMemoCommand(memoId, updateMediaMemoCommands, requestMemberId);
        }

        record MediaSequenceUpdateRequest(
                Long memoMediaId,
                Integer sequence
        ) {
            MemoMediaUpdateSequenceCommand toCommand() {
                return new MemoMediaUpdateSequenceCommand(memoMediaId, sequence);
            }
        }
    }
}
