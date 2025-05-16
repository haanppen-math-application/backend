package com.hpmath.domain.directory.service.delete.executor;

import com.hpmath.domain.directory.service.delete.DirectoryDeleteCommand;
import com.hpmath.common.Role;

interface DeleteDirectoryHandler {
    Integer process(final DirectoryDeleteCommand directoryDeleteCommand);
    boolean applicable(final Role role);
}
