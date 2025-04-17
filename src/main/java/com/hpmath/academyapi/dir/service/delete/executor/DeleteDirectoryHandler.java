package com.hpmath.academyapi.dir.service.delete.executor;

import com.hpmath.academyapi.dir.service.delete.DirectoryDeleteCommand;
import com.hpmath.academyapi.security.Role;

interface DeleteDirectoryHandler {
    Integer process(final DirectoryDeleteCommand directoryDeleteCommand);
    boolean applicable(final Role role);
}
