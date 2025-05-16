package com.hpmath.domain.directory.service.create.validate;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DirectoryOwnerValidator implements DirectoryCreateValidator {
    private final MemberClient memberClient;
    private final DirectoryRepository directoryRepository;

    @Override
    public void validate(final Directory directory) {
        final Directory parentDirectory = getParentDirectory(directory);
        if (isOverManager(directory.getOwnerId())) {
            return;
        }
        if (canAddByEveryOne(parentDirectory)) {
            return;
        }
        if (isRequestMemberIsOwner(parentDirectory, directory.getOwnerId())) {
            return;
        }
        throw new DirectoryException("디렉토리 ACCESS 권한 부재", ErrorCode.ITS_NOT_YOUR_DIRECTORY);
    }

    private Directory getParentDirectory(final Directory directory) {
        return directoryRepository.findDirectoryByPath(getParentPath(directory.getPath()))
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
    }

    private boolean isRequestMemberIsOwner(final Directory targetDirectory, final Long requestMemberId) {
        if (targetDirectory.getOwnerId().equals(requestMemberId)) {
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

    private boolean isOverManager(final Long requestMember) {
        return memberClient.isMatch(requestMember, Role.MANAGER, Role.ADMIN);
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
