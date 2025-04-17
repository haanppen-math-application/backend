package com.hpmath.academyapi.dir.service.update;

import com.hpmath.academyapi.dir.dto.UpdateDirectoryCommand;

interface DirectoryUpdateHandler {
    void update(final UpdateDirectoryCommand updateDirectoryCommand);
}
