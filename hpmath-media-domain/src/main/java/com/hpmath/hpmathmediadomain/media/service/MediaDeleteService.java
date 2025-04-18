package com.hpmath.hpmathmediadomain.media.service;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathmediadomain.media.dto.DeleteMediaCommand;
import com.hpmath.hpmathmediadomain.media.entity.Media;
import com.hpmath.hpmathmediadomain.media.exception.MediaException;
import com.hpmath.hpmathmediadomain.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MediaDeleteService {
    private final MediaRepository mediaRepository;

    @Transactional
    public void delete(final DeleteMediaCommand command) {
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
