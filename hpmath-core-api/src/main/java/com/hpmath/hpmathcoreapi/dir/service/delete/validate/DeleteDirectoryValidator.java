package com.hpmath.hpmathcoreapi.dir.service.delete.validate;

import com.hpmath.hpmathcoreapi.dir.service.delete.DirectoryDeleteCommand;

interface DeleteDirectoryValidator {
    void validate(final DirectoryDeleteCommand directoryDeleteCommand);
}
