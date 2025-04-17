package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateOnlineLessonInfoRequest(
        @NotNull Long targetCourseId,
        String title,
        String lessonRange,
        String lessonDescribe,
        Long categoryId,
        String imageSrc
) {
    public UpdateOnlineLessonInfoCommand toCommand(final Long requestMemberId, final Role requestMemberRole) {
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
