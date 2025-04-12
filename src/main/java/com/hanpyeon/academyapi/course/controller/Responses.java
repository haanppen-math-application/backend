package com.hanpyeon.academyapi.course.controller;

import com.hanpyeon.academyapi.course.application.dto.AttachmentChunkResult;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.course.domain.Student;
import com.hanpyeon.academyapi.course.domain.Teacher;
import java.time.LocalDate;
import java.util.List;

public class Responses {
    public record StudentPreviewResponse(
            Long studentId,
            String studentName,
            Integer grade
    ) {
        public static StudentPreviewResponse of(final Student student) {
            return new StudentPreviewResponse(student.id(), student.name(), student.grade());
        }
    }

    public record TeacherPreviewResponse(
            String teacherName,
            Long teacherId
    ) {
        public static TeacherPreviewResponse of(final Teacher teacher) {
            return new TeacherPreviewResponse(teacher.name(), teacher.id());
        }
    }

    public record CourseDetailResponse(
            Long courseId,
            String courseName,
            List<StudentPreviewResponse> studentPreviews,
            TeacherPreviewResponse teacherPreview
    ) {
        public static CourseDetailResponse of(final Course course) {
            return new CourseDetailResponse(
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getStudents().stream()
                            .map(StudentPreviewResponse::of)
                            .toList(),
                    TeacherPreviewResponse.of(course.getTeacher()));
        }
    }

    public record CoursePreviewResponse(
            String courseName,
            Long courseId,
            Integer studentSize,
            TeacherPreviewResponse teacherPreview
    ) {
        public static CoursePreviewResponse of(final Course course) {
            return new CoursePreviewResponse(course.getCourseName(), course.getCourseId(), course.getStudents().size(),
                    TeacherPreviewResponse.of(course.getTeacher()));
        }
    }

    public record RegisterAttachmentChunkResponse(
            Long nextChunkIndex,
            Long remainSize,
            Boolean needMore,
            Boolean isWrongChunk,
            String errorMessage
    ) {
        public static RegisterAttachmentChunkResponse of(final AttachmentChunkResult result) {
            return new RegisterAttachmentChunkResponse(
                    result.getNextRequireChunkIndex(),
                    result.getRemainSize(),
                    result.getNeedMore(),
                    result.getIsWrongChunk(),
                    result.getErrorMessage()
            );
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
