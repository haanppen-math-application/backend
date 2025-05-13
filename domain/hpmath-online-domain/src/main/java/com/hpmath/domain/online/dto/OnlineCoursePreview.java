package com.hpmath.domain.online.dto;

public record OnlineCoursePreview(
        String courseName,
        Long courseId,
        Integer studentSize,
        TeacherPreviewResponse teacherPreview,
        LessonCategoryInfo lessonCategoryInfo,
        String imageSrc
){
}