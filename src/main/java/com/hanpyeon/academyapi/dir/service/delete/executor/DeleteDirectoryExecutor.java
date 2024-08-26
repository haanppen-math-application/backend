package com.hanpyeon.academyapi.dir.service.delete.executor;

import com.hanpyeon.academyapi.dir.service.delete.DirectoryDeleteCommand;

public interface DeleteDirectoryExecutor {
    Integer delete(final DirectoryDeleteCommand directoryDeleteCommand);
}
