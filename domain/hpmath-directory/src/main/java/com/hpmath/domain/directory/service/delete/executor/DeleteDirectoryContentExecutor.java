package com.hpmath.domain.directory.service.delete.executor;

import com.hpmath.domain.directory.dao.Directory;
import java.util.Collection;

interface DeleteDirectoryContentExecutor {

    void delete(final Collection<Directory> directories);
}
