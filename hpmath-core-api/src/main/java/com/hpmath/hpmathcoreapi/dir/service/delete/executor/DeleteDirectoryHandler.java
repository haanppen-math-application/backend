package com.hpmath.hpmathcoreapi.dir.service.delete.executor;

import com.hpmath.hpmathcoreapi.dir.service.delete.DirectoryDeleteCommand;
import com.hpmath.hpmathcoreapi.security.Role;

interface DeleteDirectoryHandler {
    Integer process(final DirectoryDeleteCommand directoryDeleteCommand);
    boolean applicable(final Role role);
}
