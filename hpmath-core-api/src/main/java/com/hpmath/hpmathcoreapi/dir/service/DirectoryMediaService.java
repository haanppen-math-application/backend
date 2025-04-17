package com.hpmath.hpmathcoreapi.dir.service;

import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import com.hpmath.hpmathcoreapi.dir.dao.DirectoryRepository;
import com.hpmath.hpmathcoreapi.dir.dto.SaveMediaToDirectoryCommand;
import com.hpmath.hpmathcoreapi.dir.exception.DirectoryException;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import com.hpmath.hpmathcoreapi.media.entity.Media;
import com.hpmath.hpmathcoreapi.media.repository.MediaRepository;
import com.hpmath.hpmathcoreapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DirectoryMediaService {
    private final MediaRepository mediaRepository;
    private final DirectoryRepository directoryRepository;

    public void saveToDirectory(final SaveMediaToDirectoryCommand command) {
        final Directory directory = getDirectory(command.directoryPath());
        final Media media = loadMedia(command.mediaSrc());

        validate(directory, command.requestMemberId(), command.requestMemberRole());
        directory.add(media);
    }

    public void validate(final Directory directory, final Long requestMemberId, final Role role) {
        if (directory.getCanAddByEveryone()) {
            return;
        }
        if (isOwner(requestMemberId, directory)) {
            return;
        }
        if (isSuperUser(role)) {
            return;
        }
        throw new DirectoryException("디렉토리가 잠겨있거나, 본인 소유가 아닙니다", ErrorCode.ITS_NOT_YOUR_DIRECTORY);
    }

    private boolean isOwner(final Long requestMemberId, final Directory targetDirectory) {
        if (requestMemberId.equals(targetDirectory.getOwner().getId())) {
            return true;
        }
        return false;
    }

    private boolean isSuperUser(final Role requestMemberRole) {
        if (requestMemberRole.equals(Role.MANAGER) || requestMemberRole.equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }

    private Directory getDirectory(final String directoryPath) {
        return directoryRepository.findDirectoryByPath(directoryPath)
                .orElseThrow();
    }

    private Media loadMedia(final String mediaSrc) {
        return mediaRepository.findBySrc(mediaSrc)
                .orElseThrow();
    }
}
