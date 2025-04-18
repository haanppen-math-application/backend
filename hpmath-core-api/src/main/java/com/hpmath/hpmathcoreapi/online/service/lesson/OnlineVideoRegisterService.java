package com.hpmath.hpmathcoreapi.online.service.lesson;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourseRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineVideo;
import com.hpmath.hpmathcoreapi.online.dao.OnlineVideoRepository;
import com.hpmath.hpmathcoreapi.online.dto.AddOnlineVideoCommand;
import com.hpmath.hpmathmediadomain.media.entity.Media;
import com.hpmath.hpmathmediadomain.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineVideoRegisterService {
    private final OnlineVideoRepository onlineVideoRepository;
    private final OnlineCourseRepository onlineCourseRepository;
    private final MediaRepository mediaRepository;

    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    /**
     * @param addVideoToOnlineCourseCommand 해당 온라인 강의와 관련된 데이터를 모두 삭제 후, 업데이트 실시
     */
    @Transactional
    public void addOnlineVideo(@Validated final AddOnlineVideoCommand addVideoToOnlineCourseCommand) {
        final OnlineCourse onlineCourse = loadOnlineCourseWithVideos(addVideoToOnlineCourseCommand.onlineCourseId());
        onlineCourseOwnerValidator.validate(addVideoToOnlineCourseCommand.requestMemberRole(), addVideoToOnlineCourseCommand.requestMemberId(), onlineCourse.getTeacher().getId());

        final Media media = loadMedia(addVideoToOnlineCourseCommand.onlineVideoCommand().videoSrc());
        final Integer matchSequence = getMatchSequence(onlineCourse);
        final OnlineVideo onlineVideo = new OnlineVideo(onlineCourse, media, media.getMediaName(),
                addVideoToOnlineCourseCommand.onlineVideoCommand().isPreview(), matchSequence);

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

    private Media loadMedia(final String mediaSource) {
        return mediaRepository.findBySrc(mediaSource)
                .orElseThrow(() -> new BusinessException("미디어를 찾을 수 없음 : " + mediaSource,
                        ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
