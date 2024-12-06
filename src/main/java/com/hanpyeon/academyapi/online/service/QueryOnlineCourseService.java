package com.hanpyeon.academyapi.online.service;

import com.hanpyeon.academyapi.course.application.dto.TeacherPreview;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dto.OnlineCoursePreview;
import com.hanpyeon.academyapi.online.dto.QueryMyOnlineCourseCommand;
import com.hanpyeon.academyapi.online.dto.QueryOnlineCourseByStudentIdCommand;
import com.hanpyeon.academyapi.online.dto.QueryOnlineCourseByTeacherIdCommand;
import com.hanpyeon.academyapi.security.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryOnlineCourseService {
    private final OnlineCourseRepository onlineCourseRepository;

    public List<OnlineCoursePreview> queryAll() {
        return onlineCourseRepository.findAll().stream()
                .map(onlineCourse -> new OnlineCoursePreview(onlineCourse.getCourseName(), onlineCourse.getId(),
                        onlineCourse.getOnlineStudents().size(),
                        new TeacherPreview(onlineCourse.getTeacher().getName(), onlineCourse.getTeacher().getId())))
                .toList();
    }

    public List<OnlineCoursePreview> queryOnlineCourseByTeacherId(
            @Validated final QueryOnlineCourseByTeacherIdCommand queryOnlineCourseByTeacherIdCommand
    ) {
        return onlineCourseRepository.findAllByTeacherId(queryOnlineCourseByTeacherIdCommand.teacherId()).stream()
                .map(onlineCourse -> new OnlineCoursePreview(onlineCourse.getCourseName(), onlineCourse.getId(),
                        onlineCourse.getOnlineStudents().size(),
                        new TeacherPreview(onlineCourse.getTeacher().getName(), onlineCourse.getTeacher().getId())))
                .toList();
    }

    public List<OnlineCoursePreview> queryOnlineCourseByStudentId(
            @Validated final QueryOnlineCourseByStudentIdCommand queryOnlineCourseByStudentIdCommand) {
        return onlineCourseRepository.findAllByStudentId(queryOnlineCourseByStudentIdCommand.studentId()).stream()
                .map(onlineCourse -> new OnlineCoursePreview(onlineCourse.getCourseName(), onlineCourse.getId(),
                        onlineCourse.getOnlineStudents().size(),
                        new TeacherPreview(onlineCourse.getTeacher().getName(), onlineCourse.getTeacher().getId())))
                .toList();
    }

    public List<OnlineCoursePreview> queryMyOnlineCourses(
            @Validated final QueryMyOnlineCourseCommand queryMyOnlineCourseCommand
    ) {
        if (queryMyOnlineCourseCommand.requestMemberRole().equals(Role.STUDENT)) {
            return this.queryOnlineCourseByStudentId(new QueryOnlineCourseByStudentIdCommand(queryMyOnlineCourseCommand.requestMemberId()));
        }
        return this.queryOnlineCourseByTeacherId(new QueryOnlineCourseByTeacherIdCommand(
                queryMyOnlineCourseCommand.requestMemberId()));
    }
}
