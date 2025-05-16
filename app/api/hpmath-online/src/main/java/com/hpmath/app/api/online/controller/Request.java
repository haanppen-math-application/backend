package com.hpmath.app.api.online.controller;

import com.hpmath.domain.online.dto.AddOnlineCourseCommand;
import com.hpmath.domain.online.dto.AddOnlineVideoCommand;
import com.hpmath.domain.online.dto.AddOnlineVideoCommand.OnlineVideoCommand;
import com.hpmath.domain.online.dto.ChangeOnlineCourseInfoCommand;
import com.hpmath.domain.online.dto.OnlineCategoryAddCommand;
import com.hpmath.domain.online.dto.OnlineCourseInfoUpdateCommand;
import com.hpmath.domain.online.dto.OnlineCourseStudentUpdateCommand;
import com.hpmath.domain.online.dto.OnlineVideoPreviewUpdateCommand;
import com.hpmath.domain.online.dto.RegisterOnlineVideoAttachmentCommand;
import com.hpmath.domain.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.domain.online.dto.UpdateOnlineVideoSequenceCommand;
import com.hpmath.common.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

class Request {
    @Schema
    record AddOnlineCourseRequest(
            @Schema(example = "온라인 수업 1")
            @NotNull String courseName,
            @Schema(example = "12")
            @NotNull Long teacherId,
            @Schema(example = "[12, 21, 43, 43]")
            List<Long> students
    ) {

        AddOnlineCourseCommand toCommand(final Long requestMemberId, final Role role) {
            return new AddOnlineCourseCommand(requestMemberId, role, this.courseName, this.students, this.teacherId);
        }
    }

    record AddOnlineCourseVideoRequest(
            Long onlineCourseId,
            OnlineVideoRequest onlineVideoRequest
    ) {
        AddOnlineVideoCommand toCommand(final Long requestMemberId, final Role requestMemberRole) {
            return new AddOnlineVideoCommand(
                    onlineCourseId,
                    this.onlineVideoRequest().toCommand(),
                    requestMemberId,
                    requestMemberRole
            );
        }
        record OnlineVideoRequest(
                String videoSrc,
                Boolean isPreview
        ) {

            OnlineVideoCommand toCommand() {
                return new OnlineVideoCommand(videoSrc, isPreview);
            }
        }
    }

    record ChangeOnlineCourseInfoRequest(
            @NotNull
            Long onlineCourseId,
            String onlineCourseTitle,
            String onlineCourseRange,
            String onlineCourseContent,
            Long categoryId
    ) {
        ChangeOnlineCourseInfoCommand toCommand(final Long requestMemberId, final Role role) {
            return new ChangeOnlineCourseInfoCommand(
                    onlineCourseId,
                    onlineCourseTitle,
                    onlineCourseRange,
                    onlineCourseContent,
                    onlineCourseId,
                    requestMemberId,
                    role
            );
        }
    }

    record OnlineCourseInfoUpdateRequest(
            String courseName,
            Long newTeacherId
    ) {
        OnlineCourseInfoUpdateCommand toCommand(final Long courseId, final Long requestMemberId, final Role requestMemberRole) {
            return new OnlineCourseInfoUpdateCommand(courseId, courseName, newTeacherId, requestMemberId, requestMemberRole);
        }
    }

    record OnlineCourseStudentsUpdateRequest(
            List<Long> studentIds
    ) {
        OnlineCourseStudentUpdateCommand toCommand(final Long requestMemberId, final Long courseId) {
            return new OnlineCourseStudentUpdateCommand(requestMemberId, courseId, studentIds);
        }
    }

    record OnlineVideoPreviewUpdateRequest(
            @NotNull Long onlineVideoId,
            @NotNull Boolean previewStatus
    ) {
        OnlineVideoPreviewUpdateCommand toCommand(final Long requestMemberId, final Role requestMemberRole) {
            return new OnlineVideoPreviewUpdateCommand(onlineVideoId, previewStatus, requestMemberId, requestMemberRole);
        }
    }

    record UpdateOnlineVideoSequenceRequest(
            @NotNull Long onlineCourseId,
            @NotNull Long targetVideoId,
            @NotNull Integer updatedSequence
    ) {
        UpdateOnlineVideoSequenceCommand toCommand(final Long requestMemberId, final Role requestMemberRole) {
            return new UpdateOnlineVideoSequenceCommand(onlineCourseId, targetVideoId, updatedSequence, requestMemberId, requestMemberRole);
        }
    }

    record RegisterOnlineVideoAttachmentRequest(
            @NotBlank String attachmentTitle,
            @NotBlank String attachmentContent
    ) {
        RegisterOnlineVideoAttachmentCommand toCommand(
                final Long onlineCourseId,
                final Long onlineVideoId,
                final Long requestMemberId,
                final Role requstMemberRole
        ) {
            return new RegisterOnlineVideoAttachmentCommand(attachmentTitle, attachmentContent, onlineCourseId, onlineVideoId, requestMemberId, requstMemberRole);
        }
    }

    record UpdateOnlineLessonInfoRequest(
            @NotNull Long targetCourseId,
            String title,
            String lessonRange,
            String lessonDescribe,
            Long categoryId,
            String imageSrc
    ) {
        UpdateOnlineLessonInfoCommand toCommand(final Long requestMemberId, final Role requestMemberRole) {
            return new UpdateOnlineLessonInfoCommand(
                    targetCourseId,
                    title,
                    lessonRange,
                    lessonDescribe,
                    categoryId,
                    requestMemberId,
                    requestMemberRole,
                    imageSrc
            );
        }
    }

    public record OnlineCategoryAddRequest(
            @NotNull String categoryName,
            Long beforeCategoryId
    ) {
        public OnlineCategoryAddCommand toCommand() {
            return new OnlineCategoryAddCommand(categoryName, beforeCategoryId);
        }
    }
}
