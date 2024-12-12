package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import com.hanpyeon.academyapi.online.service.lesson.update.OnlineLessonUpdateManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineLessonUpdateService {
    private final OnlineCourseRepository onlineCourseRepository;
    private final OnlineLessonUpdateManager onlineLessonInfoUpdateManager;
    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    @Transactional
    public void updateLessonInfo(@Validated final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        final OnlineCourse onlineCourse = loadCourseAndCategoryByCourseId(updateOnlineLessonInfoCommand.targetCourseId());
        onlineCourseOwnerValidator.validate(updateOnlineLessonInfoCommand.requestMemberRole(), updateOnlineLessonInfoCommand.requestMemberId(), onlineCourse.getTeacher().getId());

        onlineLessonInfoUpdateManager.update(onlineCourse, updateOnlineLessonInfoCommand);
    }

    private OnlineCourse loadCourseAndCategoryByCourseId(final Long courseId) {
        return onlineCourseRepository.loadCourseAndCategoryByCourseId(courseId)
                .orElseThrow(() -> new BusinessException("반을 찾을 수 없습니다 : " + courseId, ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
