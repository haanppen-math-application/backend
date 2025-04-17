package com.hpmath.academyapi.dir.service.create.validate;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.dir.dao.Directory;
import com.hpmath.academyapi.dir.dao.DirectoryRepository;
import com.hpmath.academyapi.dir.exception.DirectoryException;
import com.hpmath.academyapi.exception.ErrorCode;
import com.hpmath.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DirectoryOwnerValidator implements DirectoryCreateValidator {
    private final DirectoryRepository directoryRepository;

    @Override
    public void validate(final Directory directory) {
        final Directory parentDirectory = getParentDirectory(directory);
        if (isOverManager(directory.getOwner())) {
            return;
        }
        if (canAddByEveryOne(parentDirectory)) {
            return;
        }
        if (isRequestMemberIsOwner(parentDirectory, directory.getOwner())) {
            return;
        }
        throw new DirectoryException("디렉토리 ACCESS 권한 부재", ErrorCode.ITS_NOT_YOUR_DIRECTORY);
    }

    private Directory getParentDirectory(final Directory directory) {
        return directoryRepository.findDirectoryByPath(getParentPath(directory.getPath()))
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
    }

    private boolean isRequestMemberIsOwner(final Directory targetDirectory, final Member requestMember) {
        if (targetDirectory.getOwner().getId().equals(requestMember.getId())) {
            return true;
        }
        return false;
    }

    private boolean canAddByEveryOne(final Directory directory) {
        if (directory.getCanAddByEveryone()) {
            return true;
        }
        return false;
    }

    private boolean isOverManager(final Member requestMember) {
        final Role memberRole = requestMember.getRole();
        if (memberRole.equals(Role.MANAGER) || memberRole.equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }

    private String getParentPath(final String path) {
        final int endIndex = path.lastIndexOf('/', path.length() - 2);
        if (endIndex == -1) {
            throw new DirectoryException("부모 디렉토리 존재 X", ErrorCode.NOT_EXIST_DIRECTORY);
        }
        final String parentPath = path.substring(0, endIndex + 1);
        return parentPath;
    }
}
