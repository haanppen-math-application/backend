package com.hpmath.domain.course.dto;

import com.hpmath.client.member.MemberClient.MemberInfo;
import java.time.LocalDate;
import java.util.List;

public class Responses {
    public record StudentPreviewResponse(
            Long studentId,
            String studentName,
            Integer grade
    ) {
        public static StudentPreviewResponse of(final MemberInfo memberInfo) {
            return new StudentPreviewResponse(memberInfo.memberId(), memberInfo.memberName(), memberInfo.memberGrade());
        }
    }

    public record TeacherPreviewResponse(
            String teacherName,
            Long teacherId
    ) {
        public static TeacherPreviewResponse of(final MemberInfo memberInfo) {
            return new TeacherPreviewResponse(memberInfo.memberName(), memberInfo.memberId());
        }
    }

    public record CourseDetailResponse(
            Long courseId,
            String courseName,
            List<StudentPreviewResponse> studentPreviews,
            TeacherPreviewResponse teacherPreview
    ) {
    }

    public record CoursePreviewResponse(
            String courseName,
            Long courseId,
            Integer studentSize,
            TeacherPreviewResponse teacherPreview
    ) {
        public static CoursePreviewResponse of(final Long courseId, final String courseName, final Integer studentSize,
                                               final TeacherPreviewResponse teacherPreview) {
            return new CoursePreviewResponse(courseName, courseId, studentSize, teacherPreview);
        }
    }

    public record MemoAppliedDayResponse(
            Long courseId,
            String courseName,
            Long courseMemoId,
            LocalDate registeredDateTime
    ) {
    }

    public record MemoViewResponse(
            Long memoId,
            String progressed,
            String homework,
            LocalDate targetDate,
            List<MediaViewResponse> memoMediaViews
    ) {
    }

    public record MediaViewResponse(
            Long memoMediaId,
            String mediaName,
            String mediaSource,
            Integer mediaSequence,
            List<AttachmentViewResponse> attachmentViews
    ) {
    }

    public record AttachmentViewResponse(
            Long attachmentId,
            String fileName,
            String mediaSource
    ) {
    }
}
