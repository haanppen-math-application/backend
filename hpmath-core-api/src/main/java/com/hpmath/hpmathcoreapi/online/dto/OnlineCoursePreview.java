package com.hpmath.hpmathcoreapi.online.dto;

import com.hpmath.hpmathcoreapi.course.controller.Responses.TeacherPreviewResponse;

public record OnlineCoursePreview(
        String courseName,
        Long courseId,
        Integer studentSize,
        TeacherPreviewResponse teacherPreview,
        LessonCategoryInfo lessonCategoryInfo,
        String imageSrc
){
}