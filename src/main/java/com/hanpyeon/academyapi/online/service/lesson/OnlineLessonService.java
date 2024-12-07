package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import com.hanpyeon.academyapi.online.service.lesson.update.OnlineLessonUpdateManager;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineLessonService {
    private final OnlineCourseRepository onlineCourseRepository;
    private final OnlineLessonUpdateManager onlineLessonInfoUpdateManager;

    @Transactional
    public void updateLessonInfo(@Validated final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        final OnlineCourse onlineCourse = loadCourseAndCategoryByCourseId(updateOnlineLessonInfoCommand.targetCourseId());
        validateOwner(updateOnlineLessonInfoCommand.requestMemberRole(), updateOnlineLessonInfoCommand.requestMemberId(), onlineCourse.getId());

        onlineLessonInfoUpdateManager.update(onlineCourse, updateOnlineLessonInfoCommand);
    }

    private void validateOwner(final Role requestMemberRole, final Long requestMemberId, final Long ownerId) {
        if (requestMemberId.equals(ownerId)) {
            return;
        }
        if (!requestMemberRole.equals(Role.TEACHER)) {
            return;
        }
        log.warn("theres no permissions to this user {}", ownerId);
        throw new BusinessException("온라인 수업 정보를 수정할 수 있는 권한 없음", ErrorCode.ONLINE_COURSE_EXCEPTION);
    }

    private OnlineCourse loadCourseAndCategoryByCourseId(final Long courseId) {
        return onlineCourseRepository.loadCourseAndCategoryByCourseId(courseId)
                .orElseThrow(() -> new BusinessException("반을 찾을 수 없습니다 : " + courseId, ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
