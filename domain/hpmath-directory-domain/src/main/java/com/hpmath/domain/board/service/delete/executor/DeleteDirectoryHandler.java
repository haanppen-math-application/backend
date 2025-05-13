package com.hpmath.domain.board.service.delete.executor;

import com.hpmath.domain.board.service.delete.DirectoryDeleteCommand;
import com.hpmath.hpmathcore.Role;

interface DeleteDirectoryHandler {
    Integer process(final DirectoryDeleteCommand directoryDeleteCommand);
    boolean applicable(final Role role);
}
