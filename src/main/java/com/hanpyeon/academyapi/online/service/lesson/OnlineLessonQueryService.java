package com.hanpyeon.academyapi.online.service.lesson;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dao.OnlineVideo;
import com.hanpyeon.academyapi.online.dao.OnlineVideoAttachment;
import com.hanpyeon.academyapi.online.dto.OnlineLessonDetail;
import com.hanpyeon.academyapi.online.dto.OnlineVideoAttachmentDetail;
import com.hanpyeon.academyapi.online.dto.OnlineVideoDetail;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnlineLessonQueryService {
    private final OnlineCourseRepository onlineCourseRepository;

    public OnlineLessonDetail loadDetails(final Long courseId) {
        final OnlineCourse onlineCourses = onlineCourseRepository.loadOnlineCourseAndVideosAndCategoryById(courseId)
                .orElseThrow(() -> new BusinessException(courseId + "를 찾을 수 없음", ErrorCode.ONLINE_COURSE_EXCEPTION));
        return mapToDetail(onlineCourses);
    }

    private OnlineLessonDetail mapToDetail(final OnlineCourse onlineCourse) {
        return new OnlineLessonDetail(
                onlineCourse.getId(),
                onlineCourse.getCourseTitle(),
                onlineCourse.getCourseRange(),
                onlineCourse.getCourseContent(),
                onlineCourse.getVideos().stream()
                        .map(this::mapToDetail)
                        .collect(Collectors.toList())
        );
    }

    private OnlineVideoDetail mapToDetail(final OnlineVideo onlineVideo) {
        return new OnlineVideoDetail(onlineVideo.getId(), onlineVideo.getVideoSequence(), onlineVideo.getVideoName(),
                onlineVideo.getVideoAttachments().stream()
                        .map(this::mapToDetail)
                        .toList()
        );
    }

    private OnlineVideoAttachmentDetail mapToDetail(final OnlineVideoAttachment onlineVideoAttachment) {
        return new OnlineVideoAttachmentDetail(onlineVideoAttachment.getId(),
                onlineVideoAttachment.getMedia().getSrc());
    }
}
