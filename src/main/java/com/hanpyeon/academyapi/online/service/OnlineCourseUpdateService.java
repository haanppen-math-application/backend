package com.hanpyeon.academyapi.online.service;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dto.OnlineCourseStudentUpdateCommand;
import com.hanpyeon.academyapi.online.dto.OnlineCourseInfoUpdateCommand;
import com.hanpyeon.academyapi.online.service.update.OnlineCourseStudentsUpdateHandler;
import com.hanpyeon.academyapi.online.service.update.OnlineCourseUpdateManager;
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

    private OnlineCourse findOnlineCourse(final Long courseId) {
        return onlineCourseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
