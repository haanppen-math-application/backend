package com.hanpyeon.academyapi.online.service.course;

import com.hanpyeon.academyapi.course.application.dto.TeacherPreview;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineStudent;
import com.hanpyeon.academyapi.online.dto.OnlineCourseDetails;
import com.hanpyeon.academyapi.online.dto.OnlineCoursePreview;
import com.hanpyeon.academyapi.online.dto.OnlineStudentPreview;
import com.hanpyeon.academyapi.online.dto.OnlineTeacherPreview;
import com.hanpyeon.academyapi.online.dto.QueryMyOnlineCourseCommand;
import com.hanpyeon.academyapi.online.dto.QueryOnlineCourseByStudentIdCommand;
import com.hanpyeon.academyapi.online.dto.QueryOnlineCourseByTeacherIdCommand;
import com.hanpyeon.academyapi.online.dto.QueryOnlineCourseDetailsCommand;
import com.hanpyeon.academyapi.security.Role;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnlineCourseQueryService {
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
            return this.queryOnlineCourseByStudentId(
                    new QueryOnlineCourseByStudentIdCommand(queryMyOnlineCourseCommand.requestMemberId()));
        }
        return this.queryOnlineCourseByTeacherId(new QueryOnlineCourseByTeacherIdCommand(
                queryMyOnlineCourseCommand.requestMemberId()));
    }

    public OnlineCourseDetails queryOnlineCourseDetails(
            @Validated final QueryOnlineCourseDetailsCommand queryOnlineCourseDetailsCommand
    ) {
        final OnlineCourse onlineCourse = onlineCourseRepository.findOnlineCourse(queryOnlineCourseDetailsCommand.onlineCourseId());
        final OnlineTeacherPreview onlineTeacherPreview = new OnlineTeacherPreview(onlineCourse.getTeacher().getName(), onlineCourse.getTeacher().getId());
        final List<OnlineStudentPreview> onlineStudentPreviews = onlineCourse.getOnlineStudents().stream()
                .map(OnlineStudent::getMember)
                .map(member -> new OnlineStudentPreview(member.getId(), member.getName(), member.getGrade()))
                .toList();
        return new OnlineCourseDetails(onlineCourse.getId(), onlineCourse.getCourseName(), onlineStudentPreviews, onlineTeacherPreview);
    }

    public List<OnlineCoursePreview> queryOnlineCourseByCategoryId(final Long categoryId) {
        final List<OnlineCourse> onlineCourses = onlineCourseRepository.loadOnlineCoursesByCategoryId(categoryId);
        return onlineCourses.stream()
                .map(onlineCourse -> {
                    final TeacherPreview teacherPreview = new TeacherPreview(onlineCourse.getTeacher().getName(), onlineCourse.getTeacher().getId());
                    return new OnlineCoursePreview(onlineCourse.getCourseName(), onlineCourse.getId(), onlineCourse.getOnlineStudents().size(), teacherPreview);
                }).toList();
    }
}
