package com.hpmath.hpmathcoreapi.online.service.course;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourseRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineStudentRepository;
import com.hpmath.hpmathcoreapi.online.dto.DeleteOnlineCourseCommand;
import com.hpmath.hpmathcoreapi.online.dto.OnlineCourseInfoUpdateCommand;
import com.hpmath.hpmathcoreapi.online.dto.OnlineCourseStudentUpdateCommand;
import com.hpmath.hpmathcoreapi.online.service.course.update.OnlineCourseStudentsUpdateHandler;
import com.hpmath.hpmathcoreapi.online.service.course.update.OnlineCourseUpdateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class OnlineCourseUpdateService {
    private final OnlineCourseUpdateManager onlineCourseUpdateManager;
    private final OnlineCourseStudentsUpdateHandler onlineCourseStudentsUpdateHandler;
    private final OnlineCourseRepository onlineCourseRepository;
    private final OnlineStudentRepository onlineStudentRepository;

    @Transactional
    public void changeOnlineCourseInfo(@Validated final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand) {
        final OnlineCourse onlineCourse = findOnlineCourse(onlineCourseUpdateCommand.courseId());
        onlineCourseUpdateManager.update(onlineCourse, onlineCourseUpdateCommand);
    }

    @Transactional
    public void changeOnlineCourseStudents(@Validated final OnlineCourseStudentUpdateCommand onlineCourseStudentUpdateCommand) {
        final OnlineCourse onlineCourse = findOnlineCourse(onlineCourseStudentUpdateCommand.courseId());
        onlineCourseStudentsUpdateHandler.update(onlineCourse, onlineCourseStudentUpdateCommand);
    }

    @Transactional
    public void deleteOnlineCourse(@Validated final DeleteOnlineCourseCommand deleteOnlineCourseCommand) {
        final OnlineCourse onlineCourse = findOnlineCourse(deleteOnlineCourseCommand.courseId());
        if (isOwner(onlineCourse, deleteOnlineCourseCommand.requestMemberId(), deleteOnlineCourseCommand.requestMemberRole())) {
            onlineStudentRepository.removeAllByOnlineCourseId(onlineCourse.getId());
            onlineCourseRepository.delete(onlineCourse);
            return;
        }
        throw new BusinessException("지울 권한 부재 요청 : " + deleteOnlineCourseCommand.requestMemberId() + "주인 : " + onlineCourse.getId(), ErrorCode.ONLINE_COURSE_EXCEPTION);
    }

    private boolean isOwner(final OnlineCourse onlineCourse, final Long requestMemberId, final Role requestMemberRole) {
        return onlineCourse.getTeacher().getId().equals(requestMemberId)
                || requestMemberRole.equals(Role.ADMIN)
                || requestMemberRole.equals(Role.MANAGER);
    }

    private OnlineCourse findOnlineCourse(final Long courseId) {
        return onlineCourseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
