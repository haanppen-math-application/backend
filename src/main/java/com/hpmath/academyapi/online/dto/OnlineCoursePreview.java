package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.course.controller.Responses.TeacherPreviewResponse;

public record OnlineCoursePreview(
        String courseName,
        Long courseId,
        Integer studentSize,
        TeacherPreviewResponse teacherPreview,
        LessonCategoryInfo lessonCategoryInfo,
        String imageSrc
){
}