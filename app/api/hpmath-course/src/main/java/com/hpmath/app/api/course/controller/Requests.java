package com.hpmath.app.api.course.controller;

import com.hpmath.domain.course.dto.CourseRegisterCommand;
import com.hpmath.domain.course.dto.CourseUpdateCommand;
import com.hpmath.domain.course.dto.DeleteCourseCommand;
import com.hpmath.domain.course.dto.MemoMediaUpdateSequenceCommand;
import com.hpmath.domain.course.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.dto.MemoRegisterCommand;
import com.hpmath.domain.course.dto.ModifyMemoTextCommand;
import com.hpmath.domain.course.dto.RegisterAttachmentCommand;
import com.hpmath.domain.course.dto.RegisterMemoMediaCommand;
import com.hpmath.domain.course.dto.RegisterStudentCommand;
import com.hpmath.domain.course.dto.UpdateCourseStudentsCommand;
import com.hpmath.domain.course.dto.UpdateMediaMemoCommand;
import com.hpmath.common.Role;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

class Requests {
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
        DeleteCourseCommand toCommand(final Role role, final Long loginId) {
            return new DeleteCourseCommand(courseId, role, loginId);
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
                final Long requestMemberId,
                final Role role
        ) {
            return new RegisterStudentCommand(
                    requestMemberId,
                    courseId(),
                    role,
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
            String mediaSrc
    ) {
        RegisterAttachmentCommand toCommand(final Long requestMemberId) {
            return new RegisterAttachmentCommand(
                    requestMemberId,
                    memoMediaId(),
                    mediaSrc()
            );
        }
    }

    record RegisterMemoMediaRequest(
            @Nonnull
            String mediaSource,
            @Nonnull
            Long memoId
    ) {
        RegisterMemoMediaCommand toCommand(final Long requestMemberId, final Role role) {
            return new RegisterMemoMediaCommand(mediaSource, memoId, requestMemberId, role);
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
