package com.hpmath.academyapi.online.service.course.update;

import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.dto.OnlineCourseInfoUpdateCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OnlineCourseUpdateManager {
    private final List<OnlineCourseUpdateHandler> onlineCourseUpdateHandlers;

    @Transactional(propagation = Propagation.MANDATORY)
    public void update(final OnlineCourse onlineCourse, final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand) {
        onlineCourseUpdateHandlers
                .forEach(onlineCourseUpdateHandler -> onlineCourseUpdateHandler.update(onlineCourse, onlineCourseUpdateCommand));
    }
}
