package com.hpmath.domain.online.service.course;

import com.hpmath.domain.online.dao.OnlineCategory;
import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dao.OnlineCourseRepository;
import com.hpmath.domain.online.dao.OnlineStudent;
import com.hpmath.domain.online.dto.LessonCategoryInfo;
import com.hpmath.domain.online.dto.OnlineCourseDetails;
import com.hpmath.domain.online.dto.OnlineCoursePreview;
import com.hpmath.domain.online.dto.OnlineStudentPreview;
import com.hpmath.domain.online.dto.OnlineTeacherPreview;
import com.hpmath.domain.online.dto.QueryMyOnlineCourseCommand;
import com.hpmath.domain.online.dto.QueryOnlineCourseByStudentIdCommand;
import com.hpmath.domain.online.dto.QueryOnlineCourseByTeacherIdCommand;
import com.hpmath.domain.online.dto.QueryOnlineCourseDetailsCommand;
import com.hpmath.domain.online.dto.TeacherPreviewResponse;
import com.hpmath.hpmathcore.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnlineCourseQueryService {
    private final OnlineCourseRepository onlineCourseRepository;

    public List<OnlineCoursePreview> queryAll() {
        return onlineCourseRepository.findAll().stream()
                .map(this::mapToCoursePreview)
                .toList();
    }

    public List<OnlineCoursePreview> queryOnlineCourseByTeacherId(
            final QueryOnlineCourseByTeacherIdCommand queryOnlineCourseByTeacherIdCommand
    ) {
        return onlineCourseRepository.findAllByTeacherId(queryOnlineCourseByTeacherIdCommand.teacherId()).stream()
                .map(this::mapToCoursePreview)
                .toList();
    }

    public List<OnlineCoursePreview> queryOnlineCourseByStudentId(
            final QueryOnlineCourseByStudentIdCommand queryOnlineCourseByStudentIdCommand) {
        return onlineCourseRepository.findAllByStudentId(queryOnlineCourseByStudentIdCommand.studentId()).stream()
                .map(this::mapToCoursePreview)
                .toList();
    }

    public List<OnlineCoursePreview> queryMyOnlineCourses(
            final QueryMyOnlineCourseCommand queryMyOnlineCourseCommand
    ) {
        if (queryMyOnlineCourseCommand.requestMemberRole().equals(Role.STUDENT)) {
            return this.queryOnlineCourseByStudentId(
                    new QueryOnlineCourseByStudentIdCommand(queryMyOnlineCourseCommand.requestMemberId()));
        }
        return this.queryOnlineCourseByTeacherId(new QueryOnlineCourseByTeacherIdCommand(
                queryMyOnlineCourseCommand.requestMemberId()));
    }

    public OnlineCourseDetails queryOnlineCourseDetails(
            final QueryOnlineCourseDetailsCommand queryOnlineCourseDetailsCommand
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
                .map(this::mapToCoursePreview).toList();
    }

    private OnlineCoursePreview mapToCoursePreview(final OnlineCourse onlineCourse) {
        return new OnlineCoursePreview(onlineCourse.getCourseName(), onlineCourse.getId(),
                onlineCourse.getOnlineStudents().size(),
                new TeacherPreviewResponse(onlineCourse.getTeacher().getName(), onlineCourse.getTeacher().getId()),
                mapToCategoryInfo(onlineCourse.getOnlineCategory()),
                onlineCourse.getImage() == null ? null : onlineCourse.getImage().getSrc()
        );
    }

    private LessonCategoryInfo mapToCategoryInfo(final OnlineCategory onlineCategory) {
        if (onlineCategory == null) {
            return null;
        }
        return new LessonCategoryInfo(
                onlineCategory.getId(),
                onlineCategory.getParentCategory() == null ? null : onlineCategory.getParentCategory().getCategoryName(),
                onlineCategory.getCategoryName()
        );
    }
}
