package com.hpmath.domain.directory.service;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.dto.SaveMediaToDirectoryCommand;
import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class DirectoryMediaService {
    private final DirectoryRepository directoryRepository;

    public void saveToDirectory(@Valid final SaveMediaToDirectoryCommand command) {
        final Directory directory = getDirectory(command.directoryPath());

        validate(directory, command.requestMemberId(), command.requestMemberRole());
        directory.addMedia(command.mediaSrc());
    }

    private void validate(final Directory directory, final Long requestMemberId, final Role role) {
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
        if (requestMemberId.equals(targetDirectory.getOwnerId())) {
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
}
