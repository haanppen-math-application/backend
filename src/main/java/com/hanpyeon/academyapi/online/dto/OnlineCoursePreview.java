package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.course.controller.Responses.TeacherPreviewResponse;

public record OnlineCoursePreview(
        String courseName,
        Long courseId,
        Integer studentSize,
        TeacherPreviewResponse teacherPreview,
        LessonCategoryInfo lessonCategoryInfo,
        String imageSrc
){
}