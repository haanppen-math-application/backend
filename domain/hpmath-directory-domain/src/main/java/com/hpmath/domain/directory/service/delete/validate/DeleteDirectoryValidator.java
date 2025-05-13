package com.hpmath.domain.directory.service.delete.validate;

import com.hpmath.domain.directory.service.delete.DirectoryDeleteCommand;

interface DeleteDirectoryValidator {
    void validate(final DirectoryDeleteCommand directoryDeleteCommand);
}
