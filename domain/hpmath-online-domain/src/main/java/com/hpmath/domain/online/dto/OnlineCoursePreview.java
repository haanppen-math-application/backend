package com.hpmath.domain.online.dto;

public record OnlineCoursePreview(
        String courseName,
        Long courseId,
        Integer studentSize,
        OnlineTeacherPreview teacherPreview,
        LessonCategoryInfo lessonCategoryInfo,
        String imageSrc
){
}