package com.hpmath.domain.online.service.course;

import com.hpmath.client.member.MemberClient.MemberInfo;
import com.hpmath.domain.online.OnlineCourseMemberManager;
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
import com.hpmath.common.Role;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnlineCourseQueryService {
    private final OnlineCourseRepository onlineCourseRepository;
    private final OnlineCourseMemberManager memberManager;

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
            final QueryOnlineCourseByStudentIdCommand command) {
        return onlineCourseRepository.findAllByStudentId(command.studentId()).stream()
                .map(this::mapToCoursePreview)
                .toList();
    }

    public List<OnlineCoursePreview> queryMyOnlineCourses(
            final QueryMyOnlineCourseCommand command
    ) {
        if (command.requestMemberRole().equals(Role.STUDENT)) {
            return this.queryOnlineCourseByStudentId(
                    new QueryOnlineCourseByStudentIdCommand(command.requestMemberId()));
        }
        return this.queryOnlineCourseByTeacherId(new QueryOnlineCourseByTeacherIdCommand(
                command.requestMemberId()));
    }

    public OnlineCourseDetails queryOnlineCourseDetails(final QueryOnlineCourseDetailsCommand command
    ) {
        final OnlineCourse onlineCourse = onlineCourseRepository.findOnlineCourse(command.onlineCourseId());
        final OnlineTeacherPreview onlineTeacherPreview = memberManager.getMemberDetail(onlineCourse.getTeacherId())
                .map(OnlineTeacherPreview::from)
                .orElseGet(OnlineTeacherPreview::none);
        final List<OnlineStudentPreview> onlineStudentPreviews = onlineCourse.getOnlineStudents().stream()
                .map(OnlineStudent::getMemberId)
                .parallel()
                .map(memberManager::getMemberDetail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(OnlineStudentPreview::from)
                .toList();
        return new OnlineCourseDetails(onlineCourse.getId(), onlineCourse.getCourseName(), onlineStudentPreviews, onlineTeacherPreview);
    }

    public List<OnlineCoursePreview> queryOnlineCourseByCategoryId(final Long categoryId) {
        final List<OnlineCourse> onlineCourses = onlineCourseRepository.loadOnlineCoursesByCategoryId(categoryId);
        return onlineCourses.stream()
                .map(this::mapToCoursePreview).toList();
    }

    private OnlineCoursePreview mapToCoursePreview(final OnlineCourse onlineCourse) {
        final OnlineTeacherPreview teacherPreview = memberManager.getMemberDetail(onlineCourse.getTeacherId())
                .map(OnlineTeacherPreview::from)
                .orElseGet(OnlineTeacherPreview::none);

        return new OnlineCoursePreview(onlineCourse.getCourseName(), onlineCourse.getId(),
                onlineCourse.getOnlineStudents().size(),
                teacherPreview,
                mapToCategoryInfo(onlineCourse.getOnlineCategory()),
                onlineCourse.getImageSrc() == null ? null : onlineCourse.getImageSrc()
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
