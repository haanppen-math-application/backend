package com.hpmath.domain.directory.service.query;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dto.FileView;

public interface FileViewMapper {
    FileView create(final Directory directory);
    FileView create(final String mediaSrc);
}
