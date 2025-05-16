package com.hpmath.domain.directory.service.delete.validate;

import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.domain.directory.service.delete.DirectoryDeleteCommand;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RequestMemberValidator implements DeleteDirectoryValidator {
    @Override
    public void validate(DirectoryDeleteCommand directoryDeleteCommand) {
        final Long requsestMemberId = directoryDeleteCommand.getRequestMemberId();
        final Role role = directoryDeleteCommand.getRequestMemberRole();
        if (isOverManager(role)) {
            return;
        }
        if (requsestMemberId.equals(directoryDeleteCommand.getTargetDirectory().getOwnerId())) {
            return;
        }
        throw new DirectoryException("해당 디렉토리를 지울 수 있는 권한 부재", ErrorCode.ITS_NOT_YOUR_DIRECTORY);
    }

    private boolean isOverManager(Role role) {
        if (role.equals(Role.ADMIN) || role.equals(Role.MANAGER)) {
            return true;
        }
        return false;
    }
}
