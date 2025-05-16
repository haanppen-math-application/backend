package com.hpmath.domain.directory.service.delete;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.hpmathcore.Role;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DirectoryDeleteCommand {
    private final List<Directory> directories;
    private final Directory targetDirectory;
    private final Long requestMemberId;
    private final Boolean deleteChildes;
    private final Role requestMemberRole;
}
