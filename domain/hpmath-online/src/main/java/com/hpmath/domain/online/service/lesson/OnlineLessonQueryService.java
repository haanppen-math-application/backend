package com.hpmath.domain.online.service.lesson;

import com.hpmath.client.media.MediaClient;
import com.hpmath.domain.online.dao.OnlineCategory;
import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dao.OnlineCourseRepository;
import com.hpmath.domain.online.dao.OnlineStudent;
import com.hpmath.domain.online.dao.OnlineVideo;
import com.hpmath.domain.online.dao.OnlineVideoAttachment;
import com.hpmath.domain.online.dto.LessonCategoryInfo;
import com.hpmath.domain.online.dto.OnlineLessonDetail;
import com.hpmath.domain.online.dto.OnlineLessonQueryCommand;
import com.hpmath.domain.online.dto.OnlineVideoAttachmentDetail;
import com.hpmath.domain.online.dto.OnlineVideoDetail;
import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class OnlineLessonQueryService {
    private final OnlineCourseRepository onlineCourseRepository;
    private final MediaClient mediaClient;

    public OnlineLessonDetail loadDetails(@Valid final OnlineLessonQueryCommand command) {
        final OnlineCourse onlineCourses = findOnlineCourse(command);
        return mapToDetail(onlineCourses, command.requestMemberId(), command.requestMemberRole());
    }

    private OnlineCourse findOnlineCourse(OnlineLessonQueryCommand command) {
        return onlineCourseRepository.loadOnlineCourseAndVideosAndCategoryById(command.courseId())
                .orElseThrow(() -> new BusinessException(command.courseId() + "를 찾을 수 없음",
                        ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

    private OnlineLessonDetail mapToDetail(final OnlineCourse onlineCourse, final Long requestMemberId, final Role role) {
        final boolean isIncludedStudent = checkViewable(role, requestMemberId, onlineCourse.getOnlineStudents());
        return new OnlineLessonDetail(
                onlineCourse.getId(),
                onlineCourse.getCourseTitle(),
                onlineCourse.getCourseRange(),
                onlineCourse.getCourseContent(),
                onlineCourse.getVideos().stream()
                        .map(onlineVideo -> this.mapToDetail(onlineVideo, isIncludedStudent,
                                mediaClient.getFileInfo(onlineVideo.getMediaSrc()).runtimeDuration()))
                        .collect(Collectors.toList()),
                toCategory(onlineCourse.getOnlineCategory()),
                onlineCourse.getImageSrc() == null ? null : onlineCourse.getImageSrc()
        );
    }

    private boolean checkViewable(final Role role, final Long requestMemberId, final List<OnlineStudent> studentIds) {
        if (role.equals(Role.STUDENT)) {
            return studentIds.stream()
                    .map(OnlineStudent::getMemberId)
                    .anyMatch(studentId -> studentId.equals(requestMemberId));
        }
        // 학생이 아닌 조회에선 모두 true
        return true;
    }

    private LessonCategoryInfo toCategory(final OnlineCategory onlineCategory) {
        if (Objects.isNull(onlineCategory)) {
            return null;
        }
        return new LessonCategoryInfo(onlineCategory.getId(), onlineCategory.getParentCategory().getCategoryName(),
                onlineCategory.getCategoryName());
    }

    private OnlineVideoDetail mapToDetail(final OnlineVideo onlineVideo, final boolean isIncludedMember,
                                          final Long duration) {
        if (onlineVideo.getPreview() || isIncludedMember) {
            return new OnlineVideoDetail(
                    onlineVideo.getId(),
                    onlineVideo.getVideoSequence(),
                    onlineVideo.getVideoName(),
                    onlineVideo.getPreview(),
                    onlineVideo.getMediaSrc(),
                    duration,
                    onlineVideo.getVideoAttachments().stream()
                            .map(this::mapToDetail)
                            .toList()
            );
        }
        return new OnlineVideoDetail(
                onlineVideo.getId(),
                onlineVideo.getVideoSequence(),
                onlineVideo.getVideoName(),
                onlineVideo.getPreview(),
                null,
                null,
                null
        );
    }

    private OnlineVideoAttachmentDetail mapToDetail(final OnlineVideoAttachment onlineVideoAttachment) {
        return new OnlineVideoAttachmentDetail(onlineVideoAttachment.getId(),
                onlineVideoAttachment.getTitle(),
                onlineVideoAttachment.getContent());
    }
}
