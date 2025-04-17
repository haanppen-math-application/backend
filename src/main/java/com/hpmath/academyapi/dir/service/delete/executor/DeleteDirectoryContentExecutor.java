package com.hpmath.academyapi.dir.service.delete.executor;

import com.hpmath.academyapi.dir.dao.Directory;
import java.util.Collection;

interface DeleteDirectoryContentExecutor {

    void delete(final Collection<Directory> directories);
}
