package com.hpmath.hpmathcoreapi.dir.service.delete.executor;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.dir.service.delete.DirectoryDeleteCommand;

interface DeleteDirectoryHandler {
    Integer process(final DirectoryDeleteCommand directoryDeleteCommand);
    boolean applicable(final Role role);
}
