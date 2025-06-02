package com.hpmath.domain.media.service;

import com.hpmath.common.Role;
import com.hpmath.common.ErrorCode;
import com.hpmath.domain.media.dto.DeleteMediaCommand;
import com.hpmath.domain.media.entity.Media;
import com.hpmath.domain.media.exception.MediaException;
import com.hpmath.domain.media.repository.MediaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class MediaDeleteService {
    private final MediaRepository mediaRepository;

    @Transactional
    public void delete(@Valid final DeleteMediaCommand command) {
        validate(command.role(), command.memberId(), command.mediaSrc());
        final Media media = getMedia(command.mediaSrc());
        mediaRepository.delete(media);
    }

    private Media getMedia(final String mediaSrc) {
        return mediaRepository.findBySrc(mediaSrc)
                .orElseThrow(() -> new MediaException(ErrorCode.NO_SUCH_MEDIA));
    }

    private void validate(final Role role, final Long memberId, final String mediaSrc) {
        if (role.equals(Role.MANAGER) || role.equals(Role.ADMIN)) {
            return;
        }
        final Long ownerId = mediaRepository.findOwnerId(mediaSrc).orElse(null);
        if (ownerId == null) {
            return;
        }
        if(ownerId.equals(memberId)) {
            return;
        }
        throw new MediaException("권한 없음", ErrorCode.MEDIA_ACCESS_EXCEPTION);
    }
}
