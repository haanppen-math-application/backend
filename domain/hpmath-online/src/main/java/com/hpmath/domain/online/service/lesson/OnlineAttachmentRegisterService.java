package com.hpmath.domain.online.service.lesson;

import com.hpmath.domain.online.dao.OnlineVideo;
import com.hpmath.domain.online.dao.OnlineVideoAttachment;
import com.hpmath.domain.online.dao.OnlineVideoAttachmentRepository;
import com.hpmath.domain.online.dao.OnlineVideoRepository;
import com.hpmath.domain.online.dto.RegisterOnlineVideoAttachmentCommand;
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
public class OnlineAttachmentRegisterService {
    private final OnlineVideoRepository onlineVideoRepository;
    private final OnlineVideoAttachmentRepository onlineVideoAttachmentRepository;

    private final OnlineCourseOwnerValidator onlineCourseOwnerValidator;

    @Transactional
    public void register(@Valid final RegisterOnlineVideoAttachmentCommand command) {
        final OnlineVideo onlineVideo = loadOnlineVideoById(command.onlineVideoId());
        onlineCourseOwnerValidator.validate(command.requestMemberRole(), command.requestMemberId(), onlineVideo.getOnlineCourse().getTeacherId());
        
        final OnlineVideoAttachment onlineVideoAttachment = new OnlineVideoAttachment(onlineVideo, command.attachmentTitle(), command.attachmentContent());
        onlineVideoAttachmentRepository.save(onlineVideoAttachment);
    }

    private OnlineVideo loadOnlineVideoById(final Long videoId) {
        return onlineVideoRepository.loadSingleOnlineVideoWithCourseByVideoId(videoId)
                .orElseThrow(() -> new BusinessException("비디오를 찾을 수 없습니다.", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}
