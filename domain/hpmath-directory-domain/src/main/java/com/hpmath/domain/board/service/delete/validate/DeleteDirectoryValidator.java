package com.hpmath.domain.board.service.delete.validate;

import com.hpmath.domain.board.service.delete.DirectoryDeleteCommand;

interface DeleteDirectoryValidator {
    void validate(final DirectoryDeleteCommand directoryDeleteCommand);
}
