package com.hpmath.domain.directory.service.delete;

import com.hpmath.common.Role;

interface DeleteDirectoryHandler {
    Integer process(final DirectoryDeleteTargets directoryDeleteCommand);
    boolean applicable(final Role role);
}
