package com.hpmath.academyapi.dir.service.delete.validate;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.dir.exception.DirectoryException;
import com.hpmath.academyapi.dir.service.delete.DirectoryDeleteCommand;
import com.hpmath.academyapi.exception.ErrorCode;
import com.hpmath.academyapi.security.Role;
import org.springframework.stereotype.Service;

@Service
class RequestMemberValidator implements DeleteDirectoryValidator {
    @Override
    public void validate(DirectoryDeleteCommand directoryDeleteCommand) {
        final Member dirOwner = directoryDeleteCommand.getRequestMember();
        final Role role = dirOwner.getRole();
        if (role.equals(Role.ADMIN) || role.equals(Role.MANAGER)) {
            return;
        }
        if (dirOwner.equals(directoryDeleteCommand.getRequestMember())) {
            return;
        }
        throw new DirectoryException("해당 디렉토리를 지울 수 있는 권한 부재", ErrorCode.ITS_NOT_YOUR_DIRECTORY);
    }
}
