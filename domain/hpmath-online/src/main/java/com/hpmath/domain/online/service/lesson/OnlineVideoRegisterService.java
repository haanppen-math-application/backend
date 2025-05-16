package com.hpmath.domain.online.service.lesson;

import com.hpmath.client.media.MediaClient;
import com.hpmath.client.media.MediaClient.MediaInfo;
import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dao.OnlineCourseRepository;
import com.hpmath.domain.online.dao.OnlineVideo;
import com.hpmath.domain.online.dao.OnlineVideoRepository;
import com.hpmath.domain.online.dto.AddOnlineVideoCommand;
import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineVideoRegisterService {
    private final OnlineVideoRepository onlineVideoRepository;
    private final OnlineCourseRepository onlineCourseRepository;
    private final MediaClient mediaClient;

    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    @Transactional
    public void addOnlineVideo(final AddOnlineVideoCommand addVideoToOnlineCourseCommand) {
        final OnlineCourse onlineCourse = loadOnlineCourseWithVideos(addVideoToOnlineCourseCommand.onlineCourseId());
        onlineCourseOwnerValidator.validate(addVideoToOnlineCourseCommand.requestMemberRole(), addVideoToOnlineCourseCommand.requestMemberId(), onlineCourse.getTeacherId());

        final MediaInfo mediaInfo = mediaClient.getFileInfo(addVideoToOnlineCourseCommand.onlineVideoCommand().videoSrc());
        final Integer matchSequence = getMatchSequence(onlineCourse);
        final OnlineVideo onlineVideo = new OnlineVideo(
                onlineCourse,
                mediaInfo.mediaSrc(),
                mediaInfo.mediaName(),
                addVideoToOnlineCourseCommand.onlineVideoCommand().isPreview(),
                matchSequence);

        onlineVideoRepository.save(onlineVideo);
    }

    private Integer getMatchSequence(final OnlineCourse onlineCourse) {
        final Integer newestSequence = onlineCourse.getVideos().stream()
                .map(OnlineVideo::getVideoSequence)
                .max((o1, o2) -> o1 - o2)
                .orElseGet(() -> 0);
        return newestSequence + 1;
    }

    private OnlineCourse loadOnlineCourseWithVideos(final Long onlineCourseId) {
        return onlineCourseRepository.loadCourseAndVideosAndTeacherByCourseId(onlineCourseId)
                .orElseThrow(() -> new BusinessException("온라인 수업을 찾을 수 없습니다.", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
