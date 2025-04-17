package com.hpmath.hpmathcoreapi.media.service;

import com.hpmath.hpmathcoreapi.course.adapter.out.MediaAttachmentRepository;
import com.hpmath.hpmathcoreapi.course.adapter.out.MemoMediaRepository;
import com.hpmath.hpmathcoreapi.dir.dao.DirectoryRepository;
import com.hpmath.hpmathcoreapi.dir.dto.DeleteMediaCommand;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import com.hpmath.hpmathcoreapi.media.entity.Media;
import com.hpmath.hpmathcoreapi.media.exception.MediaException;
import com.hpmath.hpmathcoreapi.media.repository.MediaRepository;
import com.hpmath.hpmathcoreapi.security.Role;
import com.hpmath.hpmathcoreapi.security.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MediaDeleteService {
    private final MediaRepository mediaRepository;
    private final DirectoryRepository directoryRepository;
    private final MemoMediaRepository memoMediaRepository;
    private final MediaAttachmentRepository mediaAttachmentRepository;

    @Transactional
    public void delete(final DeleteMediaCommand deleteMediaDto) {
        validate(deleteMediaDto.memberPrincipal(), deleteMediaDto.mediaSrc());
        final Media media = mediaRepository.findBySrc(deleteMediaDto.mediaSrc())
                        .orElseThrow(() -> new MediaException(ErrorCode.NO_SUCH_MEDIA));
        mediaAttachmentRepository.findAllByMedia(media).stream()
                        .forEach(memoMediaAttachment -> {
                            memoMediaAttachment.setNull();
                            mediaAttachmentRepository.delete(memoMediaAttachment);
                        });
        directoryRepository.findDirectoriesByMediasContaining(media).stream()
                .forEach(directory -> directory.getMedias().remove(media));
        memoMediaRepository.findAllByMedia(media).stream()
                        .forEach(memoMedia -> {
                            memoMedia.setNull();
                            memoMediaRepository.delete(memoMedia);
                        });
        mediaRepository.delete(media);
    }

    private void validate(final MemberPrincipal memberPrincipal, final String mediaSrc) {
        if (memberPrincipal.role().equals(Role.MANAGER) || memberPrincipal.role().equals(Role.ADMIN)) {
            return;
        }
        final Long ownerId = mediaRepository.findOwnerId(mediaSrc).orElse(null);
        if (ownerId == null) {
            return;
        }
        if(ownerId.equals(memberPrincipal.memberId())) {
            return;
        }
        throw new MediaException("권한 없음", ErrorCode.MEDIA_ACCESS_EXCEPTION);
    }
}
