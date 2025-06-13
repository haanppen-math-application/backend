package com.hpmath.domain.online.service.lesson;

import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dao.OnlineCourseRepository;
import com.hpmath.domain.online.dao.OnlineVideo;
import com.hpmath.domain.online.dao.OnlineVideoRepository;
import com.hpmath.domain.online.dto.OnlineLessonInitializeCommand;
import com.hpmath.domain.online.dto.OnlineVideoPreviewUpdateCommand;
import com.hpmath.domain.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.domain.online.service.lesson.update.OnlineLessonUpdateManager;
import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class OnlineLessonUpdateService {
    private final OnlineCourseRepository onlineCourseRepository;
    private final OnlineVideoRepository onlineVideoRepository;
    private final OnlineLessonUpdateManager onlineLessonInfoUpdateManager;
    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    @Transactional
    public void updateLessonInfo(@Valid final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        final OnlineCourse onlineCourse = loadCourseAndCategoryByCourseId(updateOnlineLessonInfoCommand.targetCourseId());
        onlineCourseOwnerValidator.validate(updateOnlineLessonInfoCommand.requestMemberRole(), updateOnlineLessonInfoCommand.requestMemberId(), onlineCourse.getTeacherId());

        onlineLessonInfoUpdateManager.update(onlineCourse, updateOnlineLessonInfoCommand);
    }

    @Transactional
    public void updatePreviewStauts(@Valid final OnlineVideoPreviewUpdateCommand command) {
        final OnlineVideo onlineVideo = loadVideoAndVideosByCourseId(command.onlineVideoId());
        onlineCourseOwnerValidator.validate(command.requetMemberRole(), command.requestMemerId(), onlineVideo.getOnlineCourse().getTeacherId());

        onlineVideo.setPreviewStatus(command.previewStatus());
    }

    @Transactional
    public void initializeCourse(@Valid final OnlineLessonInitializeCommand command) {
        final OnlineCourse onlineCourse = loadCourseAndVideos(command.onlineCourseId());
        onlineCourseOwnerValidator.validate(command.requetMemberROle(), command.requetMemberId(), onlineCourse.getTeacherId());

        onlineCourse.clearContents();
        onlineCourseRepository.removeAllOnlineVideoAttachmentsIn(onlineCourse.getVideos().stream()
                .map(OnlineVideo::getId)
                .toList());
        onlineCourseRepository.removeOnlineCourseVideos(onlineCourse.getId());
    }

    private OnlineCourse loadCourseAndCategoryByCourseId(final Long courseId) {
        return onlineCourseRepository.loadCourseAndCategoryByCourseId(courseId)
                .orElseThrow(() -> new BusinessException(courseId + " : 반을 찾을 수 없습니다 : " + courseId, ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

    private OnlineCourse loadCourseAndVideos(final Long courseId) {
        return onlineCourseRepository.loadOnlineCourseAndVideosByCourseId(courseId)
                .orElseThrow(() -> new BusinessException(courseId + " : 반을 찾을 수 없습니다 : " + courseId, ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

    private OnlineVideo loadVideoAndVideosByCourseId(final Long videoId) {
        return onlineVideoRepository.loadSingleOnlineVideoWithCourseByVideoId(videoId)
                .orElseThrow(() -> new BusinessException(videoId + " : 등록된 영상을 찾을 수 없습니다 : ", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

}
