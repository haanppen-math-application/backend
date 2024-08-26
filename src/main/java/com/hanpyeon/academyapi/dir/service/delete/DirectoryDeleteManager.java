package com.hanpyeon.academyapi.dir.service.delete;

import com.hanpyeon.academyapi.dir.service.delete.executor.DeleteDirectoryExecutor;
import com.hanpyeon.academyapi.dir.service.delete.validate.DirectoryDeleteValidateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DirectoryDeleteManager {

    private final DirectoryDeleteValidateManager directoryDeleteValidateManager;
    private final DeleteDirectoryExecutor deleteDirectoryExecutor;

    public void delete(final DirectoryDeleteCommand directoryDeleteCommand) {
        directoryDeleteValidateManager.validate(directoryDeleteCommand);
        deleteDirectoryExecutor.delete(directoryDeleteCommand);
    }
}
