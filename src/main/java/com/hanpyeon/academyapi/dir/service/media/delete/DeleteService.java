package com.hanpyeon.academyapi.dir.service.media.delete;

import com.hanpyeon.academyapi.dir.dto.DeleteMediaDto;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.exception.MediaException;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteService {
    private final MediaRepository mediaRepository;

    @Transactional
    public void delete(final DeleteMediaDto deleteMediaDto) {
        validate(deleteMediaDto.memberPrincipal(), deleteMediaDto.mediaSrc());
        mediaRepository.deleteByMediaSrc(deleteMediaDto.mediaSrc());
    }

    private void validate(final MemberPrincipal memberPrincipal, final String mediaSrc) {
        if (memberPrincipal.role().equals(Role.MANAGER) || memberPrincipal.role().equals(Role.ADMIN)) {
            return;
        }
        final Long ownerId = mediaRepository.findOwnerId(mediaSrc).get();
        if (ownerId == null) {
            return;
        }
        if(ownerId.equals(memberPrincipal.memberId())) {
            return;
        }
        throw new MediaException("권한 없음", ErrorCode.MEDIA_ACCESS_EXCEPTION);
    }
}
