package com.hpmath.domain.online.service.lesson;

import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dao.OnlineCourseRepository;
import com.hpmath.domain.online.dao.OnlineVideo;
import com.hpmath.domain.online.dao.OnlineVideoRepository;
import com.hpmath.domain.online.dto.DeleteOnlineCourseVideoCommand;
import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnlineVideoDeleteService {
    private final OnlineCourseRepository onlineCourseRepository;
    private final OnlineVideoRepository onlineVideoRepository;
    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    @Transactional
    public void deleteOnlineVideo(final DeleteOnlineCourseVideoCommand deleteOnlineCourseVideoCommand) {
        final OnlineCourse onlineCourse = loadOnlineCourseWithVideos(deleteOnlineCourseVideoCommand.onlineCourseId());
        onlineCourseOwnerValidator.validate(deleteOnlineCourseVideoCommand.requestMemberRole(), deleteOnlineCourseVideoCommand.requestMemberId(), onlineCourse.getTeacherId());
        final OnlineVideo targetVideo = loadTargetVideo(deleteOnlineCourseVideoCommand.onlineVideoId(), onlineCourse.getVideos());

        onlineVideoRepository.delete(targetVideo);
        updateUpperVideosSequence(targetVideo.getVideoSequence(), onlineCourse.getId());
    }

    private void updateUpperVideosSequence(final Integer criticalSequence, final Long courseId) {
        onlineVideoRepository.updateUpperVideosSequence(criticalSequence, courseId);
    }

    private OnlineVideo loadTargetVideo(final Long onlineVideoId, final List<OnlineVideo> onlineVideos) {
        return onlineVideos.stream()
                .filter(onlineVideo -> onlineVideo.getId().equals(onlineVideoId))
                .findAny()
                .orElseThrow(() -> new BusinessException("비디오 ID : " + onlineVideoId + "를 찾을 수 없습니다.", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

    private OnlineCourse loadOnlineCourseWithVideos(final Long onlineCourseId) {
        return onlineCourseRepository.loadCourseAndVideosAndTeacherByCourseId(onlineCourseId)
                .orElseThrow(() -> new BusinessException("온라인 수업을 찾을 수 없습니다.", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
