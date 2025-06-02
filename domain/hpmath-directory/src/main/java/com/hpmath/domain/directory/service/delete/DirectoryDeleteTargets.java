package com.hpmath.domain.directory.service.delete;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.common.Role;
import com.hpmath.domain.directory.exception.DirectoryException;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DirectoryDeleteTargets {
    private List<Directory> allTargets;
    private Directory targetDirectory;
    private Long requestMemberId;
    private Boolean deleteChildes;
    private Role requestMemberRole;

    public static DirectoryDeleteTargets of(List<Directory> allTargets, Directory target, Long requestMemberId, Boolean deleteChildes, Role requestMemberRole) {
        if (allTargets.size() > 1 && !deleteChildes) {
            throw new DirectoryException("자식 디렉토리가 있으며, 자식을 지우기 위한 설정 필요", ErrorCode.DIRECTORY_CANNOT_DELETE);
        }
        return new DirectoryDeleteTargets(allTargets, target, requestMemberId, deleteChildes, requestMemberRole);
    }
}
