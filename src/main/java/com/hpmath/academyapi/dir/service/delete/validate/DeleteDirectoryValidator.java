package com.hpmath.academyapi.dir.service.delete.validate;

import com.hpmath.academyapi.dir.service.delete.DirectoryDeleteCommand;

interface DeleteDirectoryValidator {
    void validate(final DirectoryDeleteCommand directoryDeleteCommand);
}
