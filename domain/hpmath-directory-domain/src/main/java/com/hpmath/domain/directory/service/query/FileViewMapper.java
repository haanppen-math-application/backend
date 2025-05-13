package com.hpmath.domain.directory.service.query;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dto.FileView;
import com.hpmath.hpmathmediadomain.media.entity.Media;

interface FileViewMapper {
    FileView create(final Directory directory);
    FileView create(final Media media);
}
