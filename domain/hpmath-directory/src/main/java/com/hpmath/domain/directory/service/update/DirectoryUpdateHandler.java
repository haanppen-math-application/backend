package com.hpmath.domain.directory.service.update;

import com.hpmath.domain.directory.dto.UpdateDirectoryCommand;

interface DirectoryUpdateHandler {
    void update(final UpdateDirectoryCommand updateDirectoryCommand);
}
