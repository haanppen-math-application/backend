package com.hanpyeon.academyapi.dir.service.create.validate;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DirectoryOwnerValidator implements DirectoryCreateValidator {
    private final DirectoryRepository directoryRepository;

    @Override
    public void validate(final CreateDirectoryCommand createDirectoryCommand) {
        final Directory targetDirectory = directoryRepository.findDirectoryByPath(createDirectoryCommand.dirPath())
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
        if (isOverManager(createDirectoryCommand.requestMember())) {
            return;
        }
        if (canAddByEveryOne(targetDirectory)) {
            return;
        }
        if (isRequestMemberIsOwner(targetDirectory, createDirectoryCommand.requestMember())) {
            return;
        }
        throw new DirectoryException(ErrorCode.ITS_NOT_YOUR_DIRECTORY);
    }

    private boolean isRequestMemberIsOwner(final Directory targetDirectory, final Member requestMember) {
        // 이전 디렉토리의 소유자가 맞는지 확인
        log.info(targetDirectory.getOwner().getId() + " " + requestMember.getId());
        if (targetDirectory.getId().equals(requestMember.getId())) {
            return true;
        }
        return false;
    }

    private boolean canAddByEveryOne(final Directory directory) {
        if (directory.getCanAddByEveryone()) {
            return true;
        }
        throw new DirectoryException(directory.getOwner().getName() + " 개인소유의 디렉토리 입니다.", ErrorCode.CANNOT_ACCESS_TO_THIS_DIRECTORY);
    }

    private boolean isOverManager(final Member requestMember) {
        final Role memberRole = requestMember.getRole();
        if (memberRole.equals(Role.MANAGER) || memberRole.equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }
}
